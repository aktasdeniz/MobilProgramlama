package com.denizaktas.blm5218;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.Serializable;
import java.util.ArrayList;

import static com.denizaktas.blm5218.MainActivity.bird;
import static com.denizaktas.blm5218.MainActivity.denizaktas;

public class Persons implements Serializable {

    private String email;
    private String name;
    private String surname;
    private String password;
    private String phone;
    private Bitmap image;


    public Persons(String email, String name, String surname, String password, String phone, Bitmap image) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.phone = phone;
        this.image = image;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public Bitmap getImage() {
        return image;
    }
    public void setImage(Bitmap image) {
        this.image = image;
    }


    public static ArrayList<Persons> getPersonsList(){
        ArrayList personsList = new ArrayList();
        personsList.add(new Persons("abc@gmail.com","abc","abc","12345","05073972692",bird));
        personsList.add(new Persons("aktas.denz@gmail.com","Deniz","Aktas","123456","123456789",denizaktas));

        return personsList;
    }

    ;
}
