package com.example.memberapp.Model;

public class User {
    String first_name, last_name , email, mobile_number ;

    public User(){ }

    public User(String first_name, String last_name, String email,String mobile_number) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.mobile_number = mobile_number;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile_number() {
        return mobile_number;
    }

}
