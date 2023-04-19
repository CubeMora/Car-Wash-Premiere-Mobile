package com.carwashpremiere.carwashpremieremobile;

public class Model_ExtraServicesCar {
    private String Title;
    private String Price;
   // private boolean IsChecked;
    private int id;

    public Model_ExtraServicesCar(int id, String title, String price) {
        Title = title;
        Price = price;
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
