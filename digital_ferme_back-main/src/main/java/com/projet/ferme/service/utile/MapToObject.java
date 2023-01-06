package com.projet.ferme.service.utile;

import java.util.Map;

public class MapToObject {
    
    private Map<String,Object> map;

    public MapToObject(Map<String, Object> map) {
        this.map = map;
    }

    public String getString(String key){
        if(map.get(key) != null)
            return map.get(key).toString();
        else
            return "";
    }

    public Long getLong(String key) {
        if(map.get(key) != null){
            try{
                return Long.parseLong(map.get(key).toString());
            }catch(NumberFormatException e){
                return Long.valueOf(0);
            }
        }
        else
            return Long.valueOf(0);
    }

    public Integer getInteger(String key) {
        if(map.get(key) != null){
            try{
                return Integer.parseInt(map.get(key).toString());
            }catch(NumberFormatException e){
                return 0;
            }
        }
        else
            return 0;
    }

    public Boolean getBoolean(String key) {
        if(map.get(key) != null)
            return Boolean.parseBoolean(map.get(key).toString());
        else
            return false;
    }

}
