package com.example.helloworldspringwebproject.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties( prefix = "frontend")
public class HelloWorldPropertyConfiguration {
    private List<String>  baseUris;

    public List<String> getBaseUris() {
        return baseUris;
    }

    public void setBaseUris(List<String> baseUris) {
        this.baseUris = baseUris;
    }
}
