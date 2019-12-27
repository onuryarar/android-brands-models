package com.example.bilmemenayp;

public class BrandClass {
    private Integer id;
    private String logo;
    private String brandName;

    public BrandClass(Integer id, String logo, String brandName) {
        this.id = id;
        this.logo = logo;
        this.brandName = brandName;
    }

    public Integer getId() {
        return id;
    }

    public String getLogo() {
        return logo;
    }

    public String getBrandName() {
        return brandName;
    }
}
