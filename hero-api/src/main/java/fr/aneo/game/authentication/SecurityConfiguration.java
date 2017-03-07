package fr.aneo.game.authentication;

import static fr.aneo.game.model.Role.ADMIN;
import static fr.aneo.game.model.Role.PLAYER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;


/**
 * Created by raouf on 07/03/17.
 */
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .withUser("player").password("password").roles(PLAYER.name()).and()
                .withUser("admin").password("password").roles(PLAYER.name(), ADMIN.name());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);

        http.authorizeRequests()
                .antMatchers("/index").permitAll()
                .antMatchers("/hero/**").hasRole(PLAYER.name())
                .antMatchers("/admin").hasRole(ADMIN.name())
                .and().formLogin().loginPage("/login").failureUrl("/auth-error")
                .and().logout().permitAll();
    }
}
