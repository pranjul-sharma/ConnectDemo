package com.test.connect.connectdemo;

import android.support.annotation.NonNull;

/**
 * Created by pranjul on 27/10/17.
 */

public class Contact implements Comparable<Contact>{
    int id;
    String name,email;
    String phone;


    public Contact(){

    }

    public Contact(String name,String email,String phone){
        this.name = name;
        this.phone = phone.replaceAll("\\s+","").replaceAll("[-]","");
        this.email = email;

    }
    public Contact(int id,String name,String email,String phone){
        this.id = id;
        this.name = name;
        this.phone = phone.replaceAll("\\s+","").replaceAll("[-]","");
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone.replaceAll("\\s+","").replaceAll("[-]","");
    }

    @Override
    public int compareTo(@NonNull Contact contact) {
        return (this.getName().compareTo(contact.getName()));
    }

}
