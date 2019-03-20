package com.example.fineart_ds.model;

public class ProductHis {
    private String nameProduct;
    private String priceProduct;
    private String DesProduct;
    private String ImageProduct;

    public ProductHis() {
    }

    public ProductHis(String nameProduct, String priceProduct, String desProduct, String imageProduct) {
        this.nameProduct = nameProduct;
        this.priceProduct = priceProduct;
        DesProduct = desProduct;
        ImageProduct = imageProduct;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getPriceProduct() {
        return priceProduct;
    }

    public void setPriceProduct(String priceProduct) {
        this.priceProduct = priceProduct;
    }

    public String getDesProduct() {
        return DesProduct;
    }

    public void setDesProduct(String desProduct) {
        DesProduct = desProduct;
    }

    public String getImageProduct() {
        return ImageProduct;
    }

    public void setImageProduct(String imageProduct) {
        ImageProduct = imageProduct;
    }
}
