package com.projet.ferme.service.outsubject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.projet.ferme.entity.outsubject.HarvestSpeculation;
import com.projet.ferme.entity.person.User;
import com.projet.ferme.entity.stocks.OutgoingStock;
import com.projet.ferme.entity.subject.Speculation;
import com.projet.ferme.exception.NoElementFoundException;
import com.projet.ferme.repository.outsubject.HarvestSpeculationRepository;
import com.projet.ferme.repository.subject.SpeculationRepository;
import com.projet.ferme.service.utile.MapResponse;
import com.projet.ferme.service.utile.UserAuthenticate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HarvestSpeculationService {

	@Autowired
	private HarvestSpeculationRepository harvestRepository;
	@Autowired
	private SpeculationRepository speculationRepository;
	@Autowired
	private OutgoingService outgoingService;
	@Autowired
	private UserAuthenticate userAuthenticate;

	public Map<String, Object> add(HarvestSpeculation harvest) {
		User user = userAuthenticate.getUserAuthenticate();
		Map<String, Object> returnValues = new HashMap<String, Object>();
		Speculation speculation = speculationRepository.getById(harvest.getSpeculation().getId());

		if (speculation == null || speculation.isPresent() == false) {
			returnValues.put("success", false);
			returnValues.put("message", "Les plantes sont déja récolté");
		} else {
			harvest.setUser(user);
			HarvestSpeculation newHarvest = harvestRepository.save(harvest);
			if (newHarvest == null) {
				returnValues.put("success", false);
				returnValues.put("harvest", newHarvest);
			} else {
				saveStock(null, newHarvest, speculation);
			}
			// Don't make present false just save the harvest obeject
			// if he want to make present get another way
			// speculation.setPresent(false);
			// speculation = speculationRepository.save(speculation);
			returnValues.put("success", true);
			returnValues.put("harvest", newHarvest);
			returnValues.put("message", "Enregistré avec succé");
		}

		return returnValues;
	}

	public Map<String, Object> put(HarvestSpeculation harvest)  {
		User user = userAuthenticate.getUserAuthenticate();
		Map<String, Object> returnValues = new HashMap<String, Object>();
		HarvestSpeculation oldHarvest = harvestRepository.getById(harvest.getId());
		if (oldHarvest == null) {
			returnValues.put("success", false);
			returnValues.put("message", "L'enregistrement n'est plus dans la base");
		} else {
			harvest.setUser(user);
			HarvestSpeculation newHarvest = harvestRepository.save(harvest);
			if (newHarvest == null) {
				returnValues.put("success", false);
				returnValues.put("message", "Echec de la mise a jour");
			} else {
				Optional<OutgoingStock> outgoingStock = outgoingService
						.findBySubjectId("speculation_".concat(oldHarvest.getId().toString())).stream().findFirst();
				saveStock(outgoingStock.get().getId(), newHarvest, newHarvest.getSpeculation());
			}
			returnValues.put("success", true);
			returnValues.put("harvest", harvest);
			returnValues.put("message", "Enregistré avec succé");

		}
		return returnValues;
	}

	public Map<String, Object> findBySpeculationId(Long id) {
		Speculation speculation = speculationRepository.findById(id).get();
		Map<String, Object> returnValues = new HashMap<String, Object>();
		List<HarvestSpeculation> harvests = harvestRepository.getBySpeculation_id(id);

		returnValues.put("success", true);
		returnValues.put("speculation", speculation);
		returnValues.put("harvests", harvests);

		return returnValues;
	}

	public Map<String, Object> delete(Long id) throws NoElementFoundException {

		Map<String, Object> returnValues = new HashMap<String, Object>();
		HarvestSpeculation harvestSpeculation = harvestRepository.getById(id);
		Speculation speculation = speculationRepository.getById(harvestSpeculation.getSpeculation().getId());
		if (speculation == null) {
			returnValues.put("success", false);
			returnValues.put("message", "Désolé on a pas pu recuperer les plantes essayez encore");
		} else {
			speculation.setPresent(true);
			harvestRepository.deleteById(id);
			HarvestSpeculation harvest = harvestRepository.getById(id);

			Optional<OutgoingStock> outgoingStock = outgoingService
					.findBySubjectId("speculation_".concat(harvest.getId().toString())).stream().findFirst();
			outgoingService.delete(outgoingStock.get().getId());

			returnValues.put("success", true);
			returnValues.put("message", "Supprimé avec succé");
			speculation = speculationRepository.save(speculation);
		}

		return returnValues;
	}

	/*
	 * private void saveSale(Long id,HarvestSpeculation harvest,Speculation
	 * speculation) { String subjectId = "speculation_"+harvest.getId().toString();
	 * Sale sale = new Sale(); sale.setId(id);
	 * sale.setProduit(speculation.getSeedName());
	 * sale.setPrice(harvest.getValeur()); sale.setQuantity(harvest.getQuantity());
	 * sale.setDate(harvest.getDate()); sale.setCreatedOn(harvest.getCreatedOn());
	 * sale.setUpdatedOn(harvest.getUpdatedOn()); sale.setSubjectId(subjectId);
	 * saleService.add(sale); }
	 */

	private void saveStock(Long id, HarvestSpeculation harvest, Speculation speculation) {
		String subjectId = "speculation_" + harvest.getId().toString();
		OutgoingStock out = new OutgoingStock();
		out.setId(id);
		out.setType("in");
		out.setCreatedOn(harvest.getCreatedOn());
		out.setUpdatedOn(harvest.getUpdatedOn());
		out.setQuantity(harvest.getQuantity());
		out.setProduit(speculation.getSeed().getSeedName());
		out.setSubjectId(subjectId);
		out.setComesFrom(harvest.getSpeculation().getPlantingName());
		out.setUser(harvest.getUser());
		outgoingService.add(out);
	}

	public Map<String, Object> doByMe(){
		User user = userAuthenticate.getUserAuthenticate();

		List<HarvestSpeculation> harvestSpeculations = harvestRepository.findByUser_id(user.getId());

		return new MapResponse().withSuccess(true).withObject(harvestSpeculations).response();
	}
}
