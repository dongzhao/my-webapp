package com.mysimplework.repositories.test;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by dzhao on 24/08/2015.
 */
@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.mysimplework.model"})
@EnableJpaRepositories(basePackages = {"com.mysimplework.repositories"})
@EnableTransactionManagement
public class RepositoryConfiguration {
}
