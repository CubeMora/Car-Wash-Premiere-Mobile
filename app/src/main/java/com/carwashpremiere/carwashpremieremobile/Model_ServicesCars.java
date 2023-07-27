package com.carwashpremiere.carwashpremieremobile;

public class Model_ServicesCars {
    private int id;
    private String name;
    private String icon;
    private String description;
    private String short_descritption;
    private String activity;

    public Model_ServicesCars(int id, String name, String icon, String description, String short_descritption,String activity) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.description = description;
        this.activity = activity;
        this.short_descritption = short_descritption;
    }

    public int getId() {
        return id;
    }

    public String getShort_descritption() {
        return short_descritption;
    }

    public void setShort_descritption(String short_descritption) {
        this.short_descritption = short_descritption;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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
