package com.example.yohan.invasiveplantcounter;

public class plant_item {

    private String plant_Name;
    private int plant_Image;

    public plant_item(String plant_Name, int plant_Image) {
        this.plant_Name = plant_Name;
        this.plant_Image = plant_Image;
    }

    public String getPlant_Name() {
        return plant_Name;
    }

    public int getPlant_Image() {
        return plant_Image;
    }
}



