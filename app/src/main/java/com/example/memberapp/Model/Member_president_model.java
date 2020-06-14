package com.example.memberapp.Model;

public class Member_president_model {
    String member_president_id;

    Member_president_model(){}

    public Member_president_model(String member_president_id){
        this.member_president_id = member_president_id;
    }

    public String getMember_president_id(){
        return member_president_id;
    }
}
