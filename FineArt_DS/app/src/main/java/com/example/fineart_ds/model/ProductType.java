package com.example.fineart_ds.model;

public class ProductType {
    public int id;
    public String productTypeName;
    public String productTypeImage;

    public ProductType(int id, String productTypeName, String productTypeImage) {
        this.id = id;
        this.productTypeName = productTypeName;
        this.productTypeImage = productTypeImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeNam) {
        this.productTypeName = productTypeName;
    }

    public String getProductTypeImage() {
        return productTypeImage;
    }

    public void setProductTypeImage(String productTypeImage) {
        this.productTypeImage = productTypeImage;
    }


}
