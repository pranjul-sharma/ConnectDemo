package com.test.connect.connectdemo;

/**
 * Created by pranjul on 27/10/17.
 */

public class Contact {
    int id;
    String name,email;
    long phone;

    public Contact(){

    }

    public Contact(String name,String email,long phone){
        this.name = name;
        this.phone = phone;
        this.email = email;
    }
    public Contact(int id,String name,String email,long phone){
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }
}
