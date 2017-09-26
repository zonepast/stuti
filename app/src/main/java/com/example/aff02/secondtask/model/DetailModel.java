package com.example.aff02.secondtask.model;

/**
 * Created by AFF02 on 31-Aug-17.
 */

public class DetailModel {

    String name,email,pass,conpass,newpass,identity;
    int contact,id;

    public DetailModel(String name, String email, String pass, String conpass,String newpass,String identity, int contact, int id) {
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.conpass = conpass;
        this.contact = contact;
        this.id = id;
        this.newpass = newpass;
        this.identity = identity;
    }

    public DetailModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getConpass() {
        return conpass;
    }

    public void setConpass(String conpass) {
        this.conpass = conpass;
    }

    public int getContact() {
        return contact;
    }

    public void setContact(int contact) {
        this.contact = contact;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNewpass() {
        return newpass;
    }

    public void setNewpass(String newpass) {
        this.newpass = newpass;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }
}
