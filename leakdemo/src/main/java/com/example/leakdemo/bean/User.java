package com.example.leakdemo.bean;

import com.example.leakdemo.model.ApiUser;

/**
 * Created by zhanghehe on 2017/7/8.
 */

public class User {

    public long id;
    public String firstname;
    public String lastname;
    public boolean isFollowing;

    public User(){}

    public User(ApiUser apiUser){
        this.id=apiUser.id;
        this.firstname=apiUser.firstName;
        this.lastname=apiUser.lastName;
    }

    @Override
    public String toString() {
        return "User{"+
                "id="+id+
                ", fitstname='"+firstname+'\''+
                ", lastname'"+lastname+'\''+
                ",isFolling="+isFollowing+
                '}';
    }
}
