package com.projet.ferme.service.homesubject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.projet.ferme.entity.homesubject.Bowl;
import com.projet.ferme.entity.homesubject.ChickenCoop;
import com.projet.ferme.entity.homesubject.Enclosure;
import com.projet.ferme.entity.homesubject.Planting;
import com.projet.ferme.repository.homesubject.BowlRepository;
import com.projet.ferme.repository.homesubject.ChikenCoopRepository;
import com.projet.ferme.repository.homesubject.EnclosureRepository;
import com.projet.ferme.repository.homesubject.PlantingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AllHomeService {
    
    @Autowired
    private BowlRepository bowlRepository;
    @Autowired
    private EnclosureRepository enclosureRepository;
    @Autowired
    private PlantingRepository plantingRepository;
    @Autowired
    private ChikenCoopRepository chikenCoopRepository;

    public Map<String,Object> getAllHome(){
        Map<String, Object> map = new HashMap<String, Object>();
        List<Bowl> bowls = bowlRepository.findAll();
        List<Enclosure> enclosures = enclosureRepository.findAll();
        List<ChickenCoop> chickenCoops = chikenCoopRepository.findAll();
        List<Planting> plantings = plantingRepository.findAll();
        
        map.put("bowls", bowls);
        map.put("enclosures", enclosures);
        map.put("chickenCoops", chickenCoops);
        map.put("plantings", plantings);

        return map;
    }
}
