 package com.projet.ferme.entity.comptability;

import java.util.List;

public class ParentResult {

    private List <Result> results;

    private Long id;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Long amount;

    public ParentResult(Long id, List <Result> results, Long amount){
        this.id=id;
        this.results=results;
        this.amount=amount;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

   

    public List <Result> getResults() {
        return results;
    }

    public void setResults(List <Result> results) {
        this.results = results;
    }


  


    
}
 