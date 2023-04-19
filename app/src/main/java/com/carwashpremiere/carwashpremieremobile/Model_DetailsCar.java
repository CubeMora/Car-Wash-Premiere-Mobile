package com.carwashpremiere.carwashpremieremobile;

public class Model_DetailsCar {
    private String Title;
    private int id;

    public Model_DetailsCar(int id, String title) {
        Title = title;
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
