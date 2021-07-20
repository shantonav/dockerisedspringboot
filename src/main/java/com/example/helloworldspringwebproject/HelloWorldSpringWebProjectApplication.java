package com.example.helloworldspringwebproject;

import com.example.helloworldspringwebproject.config.HelloWorldPropertyConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableConfigurationProperties(HelloWorldPropertyConfiguration.class )
public class HelloWorldSpringWebProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloWorldSpringWebProjectApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer(HelloWorldPropertyConfiguration frontEndPropConfig) {
        String[] baseUris = new String[ frontEndPropConfig.getBaseUris().size() ];
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        //.allowedOrigins("","");
                .allowedOrigins( frontEndPropConfig.getBaseUris().toArray( baseUris ) );
            }
        };
    }



}
