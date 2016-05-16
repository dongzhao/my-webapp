package com.mysimplework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by dzhao on 17/02/2016.
 */
@SpringBootApplication
public class AuthenticationServer {
    public static void main(final String[] args) {
        SpringApplication app = new SpringApplication(AuthenticationServer.class);
        app.run(AuthenticationServer.class, args);
        app.setShowBanner(true);
    }
}
