package com.example.leakdemo.utils;

import com.example.leakdemo.bean.User;
import com.example.leakdemo.model.ApiUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanghehe on 2017/8/7.
 */

public class Utils {
    public static List<ApiUser> getApiUserList() {
        List<ApiUser> apiUserList=new ArrayList<>();

        ApiUser apiUserOne=new ApiUser();
        apiUserOne.firstName="Amit";
        apiUserOne.lastName="Shekahar";
        apiUserList.add(apiUserOne);

        ApiUser apiUserTwo=new ApiUser();
        apiUserTwo.firstName="Manish";
        apiUserTwo.lastName="Kumar";
        apiUserList.add(apiUserTwo);

        ApiUser apiUserThree=new ApiUser();
        apiUserThree.firstName="Sumit";
        apiUserThree.lastName="Kumar";
        apiUserList.add(apiUserThree);

        return apiUserList;
    }

    public static List<User> convertApiUserListToUserList(List<ApiUser> apiUsers) {
        List<User> users=new ArrayList<>();

        for (ApiUser apiUser : apiUsers) {
            User user=new User();
            user.firstname=apiUser.firstName;
            user.lastname=apiUser.lastName;
            users.add(user);
        }


        return users;
    }

    public static List<User> getUserListWhoLovesCricket() {
        List<User> userList=new ArrayList<>();

        User userOne=new User();
        userOne.id=1;
        userOne.firstname="Amit";
        userOne.lastname="Shekhar";
        userList.add(userOne);

        User userTwo=new User();
        userTwo.id=2;
        userTwo.firstname="Mainsh";
        userTwo.lastname="Kumar";
        userList.add(userTwo);
        return userList;
    }

    public static List<User> getUserListWhoLovesFootball() {
        List<User> userList=new ArrayList<>();

        User userOne=new User();
        userOne.id=1;
        userOne.firstname="Amit";
        userOne.lastname="Shekhar";
        userList.add(userOne);

        User userTwo=new User();
        userTwo.id=3;
        userTwo.firstname="Sumit";
        userTwo.lastname="Kumar";
        userList.add(userTwo);
        return userList;
    }

    public static List<User> filterUserWhoLovesBoth(List<User> users, List<User> users2) {
        List<User> userWhoLoveBoth=new ArrayList<>();
        for (User user : users) {
            for (User user1 : users2) {
                if(user.id ==user1.id){
                    userWhoLoveBoth.add(user);
                }
            }
        }

        return userWhoLoveBoth;
    }
}
