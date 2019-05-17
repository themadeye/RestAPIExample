package com.example.restapiexample.model;

public class ResObj {
    private String message;

    public String getMessage() {//this is based on the JSON response token
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
