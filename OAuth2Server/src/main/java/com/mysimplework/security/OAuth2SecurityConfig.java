package com.mysimplework.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 * Created by dzhao on 2/11/2015.
 */
@Configuration
public class OAuth2SecurityConfig {

    @Configuration
    @EnableResourceServer
    protected static class ResourceServer extends ResourceServerConfigurerAdapter {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.csrf().disable().anonymous().disable();

            http
                    .requestMatchers().antMatchers("/rest/api/**").and()
                    .authorizeRequests()
                    .antMatchers("/rest/api/**").hasRole("ADMIN")
                    .antMatchers("/rest/api/**").access("#authentication.hasScope('read')")
                    .anyRequest().authenticated();
        }

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
            resources.resourceId("oath2demo");
        }

    }

    @Configuration
    @EnableAuthorizationServer
    protected static class OAuth2Config extends AuthorizationServerConfigurerAdapter {

        @Autowired
        private AuthenticationManager authenticationManager;

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints.authenticationManager(authenticationManager).tokenStore(tokenStore());
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.inMemory()
                    .withClient("oath2demo")
                    .authorizedGrantTypes("password", "authorization_code", "refresh_token")
                    .authorities("ADMIN")
                    .scopes("read", "write")
                    .resourceIds("oath2demo")
                    .secret("secret123")
                    .accessTokenValiditySeconds(360)
                    .redirectUris("http://localhost:8098/")
                    .autoApprove(true);
        }

        @Bean
        public InMemoryTokenStore tokenStore(){
            return new InMemoryTokenStore();
        }
    }

    @Configuration
    @EnableWebSecurity
    protected static class WebSecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable().anonymous().disable();
            http.httpBasic().and()
                    .requestMatchers().antMatchers("/**")
                    .and().authorizeRequests()
                    .anyRequest().authenticated();
        }

        @Override
        protected void configure(AuthenticationManagerBuilder authManagerBuilder)
                throws Exception {
            authManagerBuilder.inMemoryAuthentication().withUser("admin")
                    .password("admin123").roles("ADMIN");
        }
    }

    @Configuration
    protected static class AuthenticationManagerConfiguration extends GlobalAuthenticationConfigurerAdapter {

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            auth.inMemoryAuthentication().withUser("admin").password("admin123").roles("ADMIN");
        }

    }

}
