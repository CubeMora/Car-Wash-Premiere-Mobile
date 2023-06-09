package com.carwashpremiere.carwashpremieremobile;

public class Model_Shortcuts {
    private int id;
    private String name;
    private String icon;
    private String activity;

    //Constructor
    public Model_Shortcuts(int id, String name, String icon, String activity) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.activity = activity;
    }

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }
}
