package com.example.leakdemo.utils;

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
}
