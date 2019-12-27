package com.example.bilmemenayp;

public class ModelClass {
    private int id;
    private int brand_id;
    private String model;
    private String image;
    private String details;

    public ModelClass(int id, int brand_id, String model, String image, String details) {
        this.id = id;
        this.brand_id = brand_id;
        this.model = model;
        this.image = image;
        this.details = details;
    }

    public int getId() {
        return id;
    }

    public int getBrand_id() {
        return brand_id;
    }

    public String getModel() {
        return model;
    }

    public String getImage() {
        return image;
    }

    public String getDetails() {
        return details;
    }
}
