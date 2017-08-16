package com.example.leakdemo.model;

/**
 * __   __    _
 * \ \ / /_ _| |_   _ _ __
 * \ V / _` | | | | | '_ \
 * | | (_| | | |_| | | | |
 * |_|\__,_|_|\__,_|_| |_|
 * Created by Hades on 2017/8/16.
 */

public class UserDetail {
    public long id;
    public String firstname;
    public String lastname;

    @Override
    public String toString() {
        return "UserDetail{"+
                "id="+id+
                ",firstname='"+firstname+'\''+
                ",lastname='"+lastname+'\''+
                '}';
    }
}
