package it.uniroma3.siw.authentication;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AuthConfiguration{
	
	@Autowired
    private DataSource dataSource;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                    .requestMatchers(HttpMethod.GET, "/", "/index", "/login", "/registrazione", "/css/**", "/images/**", "/favicon.ico").permitAll()
                    .requestMatchers(HttpMethod.POST, "/login", "/registrazione").permitAll()
                    .requestMatchers(HttpMethod.GET, "/admin/**").hasAuthority("ROLE_ADMIN")
                    .requestMatchers(HttpMethod.POST, "/admin/**").hasAuthority("ROLE_ADMIN")
                    .requestMatchers(HttpMethod.GET, "/president/**").hasAuthority("ROLE_PRESIDENT")
                    .requestMatchers(HttpMethod.POST, "/president/**").hasAuthority("ROLE_PRESIDENT")
                    .anyRequest().authenticated()
                    
            )
            .formLogin(formLogin ->
            formLogin
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error=true")
        )
            .logout(logout ->
            logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .clearAuthentication(true)
        )
            .exceptionHandling(exceptionHandling ->
                exceptionHandling
                    .accessDeniedPage("/permessoNegato")
            );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
            .dataSource(dataSource)
            .authoritiesByUsernameQuery("SELECT username, role FROM credentials WHERE username=?")
            .usersByUsernameQuery("SELECT username, password, 1 as enabled FROM credentials WHERE username=?");
    }

}
