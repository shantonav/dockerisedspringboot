package com.example.helloworldspringwebproject.domain;

public class ViewRequest {
    private String action;
    private String data;

    public ViewRequest(String action, String data) {
        this.action = action;
        this.data = data;
    }

    public String getAction() {
        return action;
    }

    public String getData() {
        return data;
    }
}
