package com.example.lab6.pojo;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "Wizard")
public class Wizard {
    @Id
    private String _id;
    private String name;
    private String sex;
    private String position;
    private int money;
    private String school;
    private String house;
    public Wizard(){

    }
    public Wizard(String _id, String name, String gender, String position, int money, String school, String house) {
        this._id = _id;
        this.name = name;
        this.sex = gender;
        this.position = position;
        this.money = money;
        this.school = school;
        this.house = house;
    }

}
