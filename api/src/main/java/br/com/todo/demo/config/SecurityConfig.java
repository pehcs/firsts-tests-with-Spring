package br.com.todo.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserServices userDetailsService;


    // Only to know it's exists
    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .csrf().disable()
        //It's important, a typeof defense to site, i disabled here to i use
            //without need put token everytime
            // .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
                .authorizeRequests()
                    // .antMatchers("/api/").hasAnyRole("USER")
                    .anyRequest()
                        .authenticated().and()
                            .formLogin().and()
                                .httpBasic().and()
                                .logout().logoutSuccessUrl("/login?logout");
                            
                        }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // auth.inMemoryAuthentication().withUser("Pedro")
        //         .password(passwordEncoder().encode("1234"))
        //         .roles("USER", "ADMIN")
        //         .and()
        //         .withUser("teste")
        //         .password(passwordEncoder().encode("teste"))
        //         .roles("USER");

        auth.userDetailsService(userDetailsService)
            .passwordEncoder(new BCryptPasswordEncoder());
    }
}
