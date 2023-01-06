package com.projet.ferme.service.outsubject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projet.ferme.entity.outsubject.OutPoultry;
import com.projet.ferme.entity.person.User;
import com.projet.ferme.entity.stocks.OutgoingStock;
import com.projet.ferme.entity.subject.Poultry;
import com.projet.ferme.exception.NoElementFoundException;
import com.projet.ferme.repository.outsubject.OutPoultryRepository;
import com.projet.ferme.repository.subject.PoultryRepository;
import com.projet.ferme.service.utile.UserAuthenticate;

@Service
public class OutPoultryService {

	@Autowired
	private OutPoultryRepository ouPoultryrepository;
	@Autowired
	private PoultryRepository poultryRepository;
	@Autowired
	private OutgoingService outgoingService;
	@Autowired
	private UserAuthenticate userAuthenticate;

	public Map<String, Object> add(OutPoultry outPoultry)  {
		User user = userAuthenticate.getUserAuthenticate();
		Map<String, Object> returnValues = new HashMap<String, Object>();
		// Get poultry objet from database
		Poultry poultry = poultryRepository.getById(outPoultry.getPoultry().getId());
		// check if the object is present
		if (poultry == null || poultry.getPresent() == false) {
			returnValues.put("success", false);
			returnValues.put("message", "Ces sujets ne sont plus presents");
		} else {
			// check if the object have the quantity to make out
			int dif = poultry.getQuantity() - outPoultry.getQuantity();
			if (dif < 0) {
				returnValues.put("success", false);
				returnValues.put("message", "Cette quantité n'est pas disponible");
			} else {
				// Save outPoultry object
				outPoultry.setUser(user);
				OutPoultry newOutPoultry = ouPoultryrepository.save(outPoultry);
				if (newOutPoultry == null) {
					returnValues.put("success", false);
					returnValues.put("message", "L'enregistrement a échoué");
				} else {
					// Update poultry object
					if (dif == 0) {
						poultry.setQuantity(dif);
						poultry.setPresent(false);
						poultry = poultryRepository.save(poultry);
					} else {
						poultry.setQuantity(dif);
						poultryRepository.save(poultry);
					}
					saveStock(null, newOutPoultry, poultry);

					returnValues.put("message", "Enregistré avec succé");
					returnValues.put("outPoultry", newOutPoultry);
					returnValues.put("success", true);
				}
			}
		}

		return returnValues;
	}

	public Map<String, Object> findByPoultry(Long id)  throws NoElementFoundException{
		Poultry poultry = poultryRepository.findById(id).get();
		Map<String, Object> returnValues = new HashMap<String, Object>();
		List<OutPoultry> outPoultrys = ouPoultryrepository.findByPoultry_id(id);
		
		returnValues.put("success", true);
		returnValues.put("outPoultrys", outPoultrys);
		returnValues.put("poultry", poultry);

		return returnValues;

	}

	public Map<String, Object> put(OutPoultry outPoultry)  {
		User user = userAuthenticate.getUserAuthenticate();
		Map<String, Object> returnValues = new HashMap<String, Object>();
		OutPoultry oldOut = ouPoultryrepository.getById(outPoultry.getId());
		if (oldOut == null) {
			returnValues.put("success", false);
			returnValues.put("message", "Cette enregistrement n'est plus dans la base");
		} else {
			if (oldOut.getQuantity() != outPoultry.getQuantity()) {
				returnValues.put("success", false);
				returnValues.put("message",
						"La modification sur la quantité n'est pas permise veuillez contactez l'administrateur");
			} else {
				outPoultry.setUser(user);
				OutPoultry newOutPoultry = ouPoultryrepository.save(outPoultry);
				if (newOutPoultry == null) {
					returnValues.put("success", false);
					returnValues.put("message", "L'enregistrement a échoué");
				} else {
					Poultry poultry = poultryRepository.getById(outPoultry.getPoultry().getId());
					Optional<OutgoingStock> outgoingStock = outgoingService
							.findBySubjectId("poultry_".concat(oldOut.getId().toString())).stream()
							.filter(item -> item.getCreatedOn().equals(outPoultry.getCreatedOn())).findFirst();
					saveStock(outgoingStock.get().getId(), newOutPoultry, poultry);

					returnValues.put("success", true);
					returnValues.put("outPoultry", outPoultry);
					returnValues.put("message", "Enregistré avec succé");

				}
			}
		}

		return returnValues;
	}

	public Map<String, Object> delete(Long id) throws NoElementFoundException  {

		Map<String, Object> returnValues = new HashMap<String, Object>();
		OutPoultry outPoultry = ouPoultryrepository.getById(id);
		Poultry poultry = outPoultry.getPoultry();

		if (poultry == null) {
			returnValues.put("success", false);
			returnValues.put("message", "Désolé on a pas pu récuperer les informations de la bande essayez encore");
		} else {
			poultry.setPresent(true);
			poultry.setQuantity(poultry.getQuantity() + outPoultry.getQuantity());
			poultryRepository.save(poultry);
			ouPoultryrepository.deleteById(id);

			Optional<OutgoingStock> outgoingStock = outgoingService
					.findBySubjectId("poultry_".concat(outPoultry.getId().toString())).stream()
					.filter(item -> item.getCreatedOn().equals(outPoultry.getCreatedOn())).findFirst();
			;
			outgoingService.delete(outgoingStock.get().getId());
		}

		returnValues.put("success", true);
		returnValues.put("message", "Supprimé avec succé");

		return returnValues;
	}

	/*
	 * private void saveSale(Long id,OutPoultry outPoultry,Poultry poultry) { String
	 * subjectId = "poultry_"+outPoultry.getId().toString(); Sale sale = new Sale();
	 * sale.setId(id); sale.setProduit(poultry.getCategoryName());
	 * sale.setPrice(outPoultry.getValeur());
	 * sale.setQuantity(outPoultry.getQuantity());
	 * sale.setDate(outPoultry.getDate());
	 * sale.setCreatedOn(outPoultry.getCreatedOn());
	 * sale.setUpdatedOn(outPoultry.getUpdatedOn()); sale.setSubjectId(subjectId);
	 * saleService.add(sale); }
	 */

	private void saveStock(Long id, OutPoultry outPoultry, Poultry poultry) {
		String subjectId = "poultry_" + outPoultry.getId().toString();
		OutgoingStock out = new OutgoingStock();
		out.setId(id);
		out.setType("in");
		out.setCreatedOn(outPoultry.getCreatedOn());
		out.setUpdatedOn(outPoultry.getUpdatedOn());
		out.setQuantity(outPoultry.getQuantity());
		out.setProduit(poultry.getCategoryName());
		out.setSubjectId(subjectId);
		out.setComesFrom(outPoultry.getPoultry().getCoopsName());
		out.setUser(outPoultry.getUser());
		outgoingService.add(out);
	}

}
