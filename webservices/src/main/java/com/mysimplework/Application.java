package com.mysimplework;

import org.h2.server.web.WebServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * Created by dzhao on 17/02/2016.
 */
@SpringBootApplication
public class Application {
    public static void main(final String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.run(Application.class, args);
        app.setShowBanner(true);
    }

    @Bean
    public ServletRegistrationBean h2servletRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new WebServlet());
        registration.addUrlMappings("/console/*");
        return registration;
    }
}
