package fr.aneo.game.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by raouf on 14/03/17.
 */
@Slf4j
@Configuration
@ComponentScan(basePackageClasses = SpringWebSecurityConfig.class)
@EnableWebSecurity
public class SpringWebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private JWTService jwtService;
    @Autowired
    private HeroLoginAuthenticationProvider ajaxAuthenticationProvider;

    @Bean
    protected ObjectMapper objectMapper() {
        return this.objectMapper;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.headers().cacheControl();
        http.csrf().disable()
                .authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/beans").permitAll()
                .antMatchers("h2-console").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JWTSigninFilter("/login", authenticationManager(), jwtService, objectMapper), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JWTAuthFilter(jwtService), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(ajaxAuthenticationProvider);
    }
}
