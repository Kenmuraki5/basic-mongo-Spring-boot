package com.example.lab6.pojo;

import java.util.ArrayList;

public class Wizards {
    private ArrayList<Wizard> model;

    // สร้าง getters และ setters สำหรับ model
    public Wizards(){
        model = new ArrayList<>();
    }

    public ArrayList<Wizard> getModel() {
        return model;
    }

    public void setModel(ArrayList<Wizard> model) {
        this.model = model;
    }
}
