package com.projet.ferme.service.dayworker;

import com.projet.ferme.entity.dayworker.DayWorker;
import com.projet.ferme.entity.stocks.Shop;
import com.projet.ferme.repository.dayworker.DayWorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service

public class DayworkerService {

    @Autowired
    private DayWorkerRepository dayWorkerRepository;

    public Map<String, Object> addDayWorker(DayWorker dayWorker) {

        Map<String, Object> map = new HashMap<String, Object>();
        dayWorker.setUpdatedOn(new Date(System.currentTimeMillis()));
        dayWorker.setCreatedOn(new Date(System.currentTimeMillis()));
        DayWorker savedDayWorker = dayWorkerRepository.save(dayWorker);
        if (savedDayWorker == null) {
            map.put("success", false);
            map.put("message", "L'enregistrement a échoué");
        }else {
            map.put("success", true);
            map.put("DayWorker", savedDayWorker);
            map.put("message", "Enregistrement reussi");
        }
        return map;
    }
    public Map<String, Object> updateDayWorker(DayWorker dayWorker) {

        Map<String, Object> map = new HashMap<String, Object>();
        Optional<DayWorker> oldworker = dayWorkerRepository.findById(dayWorker.getId());
        if (oldworker.isEmpty()) {
            map.put("success", false);
            map.put("message", "L'enregistrement a échoué, cette boutique n'existe pas");
        }else {
        	
            oldworker.get().setAddress(dayWorker.getAddress());
            oldworker.get().setFirstName(dayWorker.getFirstName());
            oldworker.get().setPhone(dayWorker.getPhone());
            oldworker.get().setUsed(dayWorker.isUsed());
            oldworker.get().setUpdatedOn(new Date(System.currentTimeMillis()));
            oldworker.get().setLastName(dayWorker.getLastName());
            DayWorker savedDayWorker = dayWorkerRepository.save(oldworker.get());
            if (savedDayWorker == null) {
                map.put("success", false);
                map.put("message", "L'enregistrement a échoué");
            }else {
                map.put("success", true);
                map.put("shop", savedDayWorker);
                map.put("message", "Enregistrement reussi");
            }
        }
        return map;
    }

    public Map<String, Object> findAllDayWorker(){

        Map<String, Object> map = new HashMap<String, Object>();
        List<DayWorker> dayWorkers = dayWorkerRepository.findAll();
        map.put("success", true);
        map.put("dayWorkers", dayWorkers);

        return map;
    }
    public Map<String, Object> findDayWorkerById(Long id){

        Map<String, Object> map = new HashMap<String, Object>();
        Optional<DayWorker> dayWorker =dayWorkerRepository.findById(id);
        map.put("succes",true);
        map.put("dayworker",dayWorker);

        return map;

    }

}
