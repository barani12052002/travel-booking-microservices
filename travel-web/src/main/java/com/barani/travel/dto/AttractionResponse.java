package com.barani.travel.dto;

import java.math.BigDecimal;

public class AttractionResponse {

    private String code;
    private String displayName;
    private String description;
    private String imageUrl;
    private BigDecimal price;
    private String duration;
    private Integer availableSlots;
    private Double rating;

    public AttractionResponse() {
    }

    public AttractionResponse(String code,
                              String displayName,
                              String description,
                              String imageUrl,
                              BigDecimal price,
                              String duration,
                              Integer availableSlots,
                              Double rating) {
        this.code = code;
        this.displayName = displayName;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
        this.duration = duration;
        this.availableSlots = availableSlots;
        this.rating = rating;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Integer getAvailableSlots() {
        return availableSlots;
    }

    public void setAvailableSlots(Integer availableSlots) {
        this.availableSlots = availableSlots;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}