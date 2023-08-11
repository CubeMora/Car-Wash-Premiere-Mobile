package com.carwashpremiere.carwashpremieremobile;

public class Model_CarTypes {
    private int id;
    private String title;

    public Model_CarTypes(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return title;
    }
}

