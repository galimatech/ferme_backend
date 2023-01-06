package com.projet.ferme.service.comptability;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import com.projet.ferme.entity.comptability.Compte;
//import com.projet.ferme.entity.comptability.Operation;
import com.projet.ferme.entity.comptability.ParentResult;
//import com.projet.ferme.entity.comptability.Result;
import com.projet.ferme.repository.comptability.CompteRepository;
//import com.projet.ferme.repository.comptability.OperationRepository;

@Service
public class ParentResultService {
    //@Autowired
    //private OperationRepository operationRepository;
    @Autowired CompteRepository compteRepository;
    public Map<String, Object> findAllOperationDetails(){

        Map<String, Object> map = new HashMap<String, Object>();
        
        List<ParentResult>  resultParents = new ArrayList<ParentResult>();
       

        /* 
        List<Operation> operations = operationRepository.findAll(); 
        List<Compte>  comptes= operations.stream().map(op->op.getCompte()).distinct().collect(Collectors.toList());

        List<Compte>  comptesParent = operations.stream().map(op->op.getCompte().getParent()).distinct().collect(Collectors.toList());
       
        comptesParent.forEach(c ->{
            List<Compte> sousComptes = comptes.stream().filter(cc->cc.getParent().equals(c)).collect(Collectors.toList());
            Long amount = operations.stream().filter(op->op.getCompte().getParent().equals(c)).mapToLong(op-> op.getAmount()).sum();
            List<Result>  results = new ArrayList<Result>();
            sousComptes.forEach(c2 ->{
                Long amount2 = operations.stream().filter(op->op.getCompte().equals(c2)).mapToLong(op-> op.getAmount()).sum();
                results.add(new Result(c2.getId(), c2, amount2));

            }); 
        
            ParentResult resForParent = new ParentResult(c.getId(), results, amount);
            resultParents.add(resForParent);
        
       });  */ 

       map.put("resultParents", resultParents);

    return map;
       
    }
    
}
 