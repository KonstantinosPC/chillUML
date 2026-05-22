package chill.guys.chillUML.securityconfig;

import chill.guys.chillUML.services.UserServices;
import chill.guys.chillUML.services.UserServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import jakarta.servlet.DispatcherType;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.support.SessionFlashMapManager;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired
    private CustomSecurityConfiguration customSecurityConfiguration;

    @Autowired
    private UserServicesImpl userServices;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new MessageDigestPasswordEncoder("SHA-256");
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception{
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userServices);

        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/auth/login", "/auth/signup", "/auth/save", "/auth/loguser", "/auth/logout", "/error").permitAll()
                        .requestMatchers("/css/**", "/assets/**", "/static/**").permitAll()
                        .requestMatchers("/dashboard", "/profile", "/profile/**", "/project", "/project/**").authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/auth/login")
                        .failureHandler((request, response, exception) -> {
                            FlashMap flashMap = new FlashMap();
                            flashMap.put("error", "Invalid username or password. Please try again.");
                            FlashMapManager flashMapManager = new SessionFlashMapManager();
                            flashMapManager.saveOutputFlashMap(flashMap, request, response);
                            response.sendRedirect("/auth/login");
                        })
                        .successHandler(customSecurityConfiguration)
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .logoutSuccessUrl("/auth/login")
                        .permitAll()
                )
                .authenticationProvider(authenticationProvider());
        return http.build();
    }
}
