package com.projet.ferme.service.utile;

import java.util.HashMap;
import java.util.Map;

public class MapResponse {
    
    private boolean success;

    private String message;

    private Object object;

    private Object arrayObject;

    private Object childArrayObject;

    public MapResponse withSuccess(boolean success){
        this.success = success;
        return this;
    }

    public MapResponse withMessage(String message) {
        this.message = message;
        return this;
    }


    public MapResponse withObject(Object object){
        this.object = object;
        return this;
    }

    public MapResponse withArrayObject(Object object){
        this.arrayObject = object;
        return this;
    }

    public MapResponse withChildArrayObject(Object object){
        this.childArrayObject = object;
        return this;
    }

    public Map<String,Object> response(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", success);
        map.put("message", message);
        map.put("object", object);
        map.put("objectArray", arrayObject);
        map.put("objectArrayChild", childArrayObject);

        return map;
    }
}

       