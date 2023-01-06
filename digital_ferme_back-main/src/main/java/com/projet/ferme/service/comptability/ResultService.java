package com.projet.ferme.service.comptability;

/* 
import java.util.stream.Collectors;
import com.projet.ferme.entity.comptability.Result;
import com.projet.ferme.repository.comptability.ResultRepository; */
import com.projet.ferme.repository.comptability.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;

import com.projet.ferme.entity.comptability.Operation;
import com.projet.ferme.entity.comptability.Compte;

import com.projet.ferme.entity.comptability.Result;
//import com.projet.ferme.entity.utils.TimeModel;

import org.springframework.stereotype.Service;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
//import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

@Service
public class ResultService {

   /*  @Autowired
    private ResultRepository resultRepository; */
    //@Autowired
    //private CategoryCompteRepository categoryCompteRepository;

    @Autowired
    private OperationRepository operationRepository;

    public Map<String, Object> findAllOperation(){

       Map<String, Object> map = new HashMap<String, Object>();
  
        List<Operation> operations = operationRepository.findAll();

        List<Result> finalResults = new ArrayList<Result>();
        //List<Result> pResults = new ArrayList<Result>();
        
        List<Compte> comptes= operations.stream().map(op->op.getCompte()).
        filter(e -> e.getParent() != null).distinct().collect(Collectors.toList());
        
        int currentYear= LocalDateTime.now().getYear();
         
        comptes.forEach(c -> {
        // Liste des operations
		Stream<Operation> filteredOperations = operations.stream().
        filter(o -> o.getCompte().getId().equals(c.getId()) && (o.getDate().getYear() == currentYear || o.getDate().getYear() == currentYear-1
        ));

        Map<Integer, Long> amountByYear = filteredOperations.
        collect(Collectors.groupingBy(fo -> fo.getDate().getYear(), 
        Collectors.summingLong(Operation::getAmount)));
        //System.out.println(amountByYear);  
        // Reuperation de la liste d'objets Result
        List<Result> newResults = amountByYear.entrySet().stream().map(e -> new Result
        (c.getId(), c,e.getValue(), c.getParent(), e.getKey())).collect(Collectors.toList());
       //results.add(new Result(c.getId(),c,amount,c.getParent(),2022));
       finalResults.addAll(newResults);
        //System.out.println(results);
            
        });

        finalResults.stream().forEach(e -> System.out.println("year : "+e.getYear() + " --> amount : " +e.getAmount()+ " --> parent : "+e.getParent().getNumber()));
		
        List<Compte> listCompteParent = finalResults.stream().map(Result::getParent).
        filter(Objects::nonNull).distinct().collect(Collectors.toList());

		listCompteParent.removeAll(Collections.singletonList(null));

		listCompteParent.forEach(cp -> {
			Stream<Result> filteredResults = finalResults.stream().
            filter(res -> res.getParent()== null || res.getCompte().getParent().getNumber() == cp.getNumber());

			Map<Integer, Long> amountParentByYear = filteredResults.
            collect(Collectors.groupingBy(Result::getYear, Collectors.summingLong(Result::getAmount)));

			List<Result> resultsParent = amountParentByYear.entrySet().stream().map(
                e -> new Result(cp.getId(), cp,e.getValue(), cp.getParent()==null?cp:cp.getParent(), e.getKey())).collect(Collectors.toList());
                finalResults.addAll( resultsParent);
            //System.out.println(newpResults);
            //Long amount = results.stream().filter(rst->rst.getParent().equals(p)).mapToLong(rst-> rst.getAmount()).sum();
            //pResults.add(new Result(p.getId(),p,amount,null));
        });

        //results.stream().forEach(e -> System.out
		//.println(" --> id : " + e.getId() + " year : " + e.getYear() + " --> amount : " + e.getAmount()));
        //results.addAll(pResults);

        finalResults.stream().forEach(e -> System.out
		.println(" --> id : " + e.getId() + " year : " + e.getYear() + " --> amount : " + e.getAmount() +  " --> parent : "+e.getParent()));
        map.put("success", true);
        map.put("results", finalResults); 
        return map;  
    }

            //Long amount = operations.stream().filter(op->op.getCompte().equals(c) && op.getDate().getYear()==currentYear || op.getDate().getYear()==currentYear-1).mapToLong(op-> op.getAmount()).sum();
            //Map<Integer,Long> e=operations.stream().filter(op->op.getCompte().equals(c) && op.getDate().getYear()==currentYear || op.getDate().getYear()==currentYear-1).collect(Collectors.groupingBy(op-> op.getDate().getYear(), 
            //Collectors.summingLong(Operation::getAmount)));
            //results.add(new Result(c.getId(),c,amount,c.getParent(),2022));

    /* public Map<String, Object> findAllOperationByYear(LocalDateTime date){

        // List<Result> results = new ArrayList<>();

       Map<String, Object> map = new HashMap<String, Object>();
        //Operation oper = new Operation();
        List<Operation> operations = operationRepository.findAll();
        List<Operation> operationsByYears= operationRepository.findOperationByYear(date);
        List<Result> results = new ArrayList<Result>();
        List<Result> pResults = new ArrayList<Result>();
        List<Compte> comptes= operations.stream().map(op->op.getCompte()).distinct().collect(Collectors.toList());
        //Date in = new Date();
        //LocalDateTime date = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
        //Date out = Date.from(date.atZone(ZoneId.systemDefault()).toInstant());

        comptes.forEach(c -> {
            Long amount = operationsByYears.stream().filter(op->op.getCompte().equals(c)).mapToLong(op-> op.getAmount()).sum();
            //Map<LocalDateTime, Long> amounts=operations.stream().collect( Collectors.groupingBy(Operation::getDate, Collectors.filtering(op->op.getCompte().equals(c), null)));
            //List<LocalDateTime> dateOperation=  operations.stream().filter(op->op.getCompte().equals(c)).map(Operation::getDate).collect(Collectors.toList());
            results.add(new Result(c.getId(),c,amount,c.getParent()));
        });
        List<Compte> parents = results.stream().map(p -> p.getParent()).distinct().collect(Collectors.toList());

        parents.forEach( p ->{
            Long amount = results.stream().filter(rst->rst.getParent().equals(p)).mapToLong(rst-> rst.getAmount()).sum();
            //Optional<LocalDateTime> dateOperation=  results.stream().map(Result::getDate).findAny();
            pResults.add(new Result(p.getId(),p,amount,null));
        });
        results.addAll(pResults);
        map.put("success", true);
        map.put("results", results); 
        return map;  
    } */

         //Operation oper = new Operation();

        //List<Result> results= resultRepository.findAll();
        //List<CategoryCompte> comptes = categoryCompteRepository.findAll();
                /*List<Operation> operationSubCompte = operations.stream().filter(oper ->oper.getCompte().getParent()!=null).
        collect(Collectors.toList());
        System.out.println(operations);
        System.out.println(operationSubCompte); */ 
        //Date in = new Date();
        //LocalDateTime date = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
        //Date out = Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
                    //Map<LocalDateTime, Long> amounts=operations.stream().collect( Collectors.groupingBy(Operation::getDate, Collectors.filtering(op->op.getCompte().equals(c), null)));
            //List<LocalDateTime> dateOperation=  operations.stream().filter(op->op.getCompte().equals(c)).map(Operation::getDate).collect(Collectors.toList());

     
}
