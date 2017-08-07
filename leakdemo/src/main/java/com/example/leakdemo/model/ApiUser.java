package com.example.leakdemo.model;

/**
 * Created by zhanghehe on 2017/8/7.
 */

public class ApiUser {

    public long id;
    public String firstName;
    public String lastName;

    @Override
    public String toString() {
        return "ApiUser{"+
                "id="+ id+
                ", firstname ='"+firstName+ '\''+
                ", lastname ='"+ lastName+'\''+
                "}";

    }
}
