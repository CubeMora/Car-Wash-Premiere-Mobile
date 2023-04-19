package com.carwashpremiere.carwashpremieremobile;

public class Model_Category {
    private int id;
    private String name;
    private String image;
    private String description;
    private String activity;

    //Constructor
    public Model_Category(int id, String name, String image, String description, String activity) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
        this.activity = activity;
    }

    //Setters and Getters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }
}
