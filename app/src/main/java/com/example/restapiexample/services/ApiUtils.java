package com.example.restapiexample.services;

public class ApiUtils {
    public static final String BASE_URL = "http://203.116.15.18/MobileSvc/api/Test/Login/";

    public static UserService getUserService(){
        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }
}
