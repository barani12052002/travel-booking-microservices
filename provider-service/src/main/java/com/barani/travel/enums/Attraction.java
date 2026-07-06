package com.barani.travel.enums;

public enum Attraction {

    OOTY("OOTY001","Ooty"),
    GOA("GOA001","Goa"),
    KERALA("KER001","Kerala"),
    KODAIKANAL("KOD001","Kodaikanal"),
    MYSORE("CHE","Chennai");

    private final String code;
    private final String displayName;

    Attraction(String code, String displayName){
        this.code = code;
        this.displayName = displayName;
    }

    public String getCode(){
        return code;
    }

    public String getDisplayName(){
        return displayName;
    }

}