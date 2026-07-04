package com.barani.travel.dto;

public class AttractionResponse {

    private String code;
    private String name;

    public AttractionResponse() {
    }

    public AttractionResponse(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}