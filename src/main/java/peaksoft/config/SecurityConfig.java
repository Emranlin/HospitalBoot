package peaksoft.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public UserDetailsService userDetails() {
        User.UserBuilder userBuilder = User.withDefaultPasswordEncoder();
        UserDetails admin = userBuilder
                .username("Zhiidegul")
                .password("zhiide123")
                .roles("ADMIN")
                .build();

        UserDetails doctor = userBuilder
                .username("Nuriza")
                .password("nuriza123")
                .roles("DOCTOR")
                .build();

        UserDetails patient = userBuilder
                .username("Eliza")
                .password("eliza123")
                .roles("PATIENT")
                .build();
        return new InMemoryUserDetailsManager(admin, doctor, patient);

    }
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers("/hospital").permitAll()
                .requestMatchers("/hospital/new").hasRole("ADMIN")
                .requestMatchers("/hospital/save").hasRole("ADMIN")
                .requestMatchers("/details/{hospitalId}").hasAnyRole("ADMIN", "DOCTOR", "USER")
                .requestMatchers("/{hospitalId}/delete").hasRole("ADMIN")
                .requestMatchers("/{hospitalId}/edit").hasRole("ADMIN")
                .requestMatchers("/{hospitalId}/update").hasRole("ADMIN")

                .requestMatchers("/doctor/{hospitalId}").hasAnyRole("ADMIN", "USER")
                .requestMatchers("/doctor/{hospitalId}/new").hasRole("ADMIN")
                .requestMatchers("/doctor/{hospitalId}/save").hasRole("ADMIN")
                .requestMatchers("/doctor/{hospitalId}/{id}/delete").hasRole("ADMIN")
                .requestMatchers("/doctor/{hospitalId}/{id}/edit").hasRole("ADMIN")
                .requestMatchers("/doctor/{hospitalId}/{id}/update").hasRole("ADMIN")
                .requestMatchers("/doctor/{hospitalId}/{doctorId}/assign").hasRole("ADMIN")
                .requestMatchers("/doctor/{hospitalId}/{doctorId}/assigned").hasRole("ADMIN")

                .requestMatchers("/department/{id}").hasAnyRole("ADMIN", "DOCTOR", "USER")
                .requestMatchers("/department/{hospitalId}/new").hasRole("ADMIN")
                .requestMatchers("/department/{hospitalId}/save").hasRole("ADMIN")
                .requestMatchers("/department/{hospitalId}/{id}/edit").hasRole("ADMIN")
                .requestMatchers("/department/{hospitalId}/{id}/update").hasRole("ADMIN")
                .requestMatchers("/department/{hospitalId}/{id}/delete").hasRole("ADMIN")
                .requestMatchers("/department/{hospitalId}/{departmentId}/doctors").hasAnyRole("ADMIN", "DOCTOR", "USER")

                .requestMatchers("/patient/{hospitalId}").hasAnyRole("ADMIN", "DOCTOR", "USER")
                .requestMatchers("/patient/{hospitalId}/new").hasAnyRole("ADMIN", "DOCTOR")
                .requestMatchers("/patient/{hospitalId}/save").hasAnyRole("ADMIN", "DOCTOR")
                .requestMatchers("/patient/{hospitalId}/{id}/edit").hasAnyRole("ADMIN", "DOCTOR")
                .requestMatchers("/patient/{hospitalId}/{id}/update").hasAnyRole("ADMIN", "DOCTOR")
                .requestMatchers("/patient/{hospitalId}/{id}/delete").hasAnyRole("ADMIN", "DOCTOR")

                .requestMatchers("/appointment/{hospitalId}").hasAnyRole("ADMIN", "DOCTOR", "USER")
                .requestMatchers("/appointment/{hospitalId}/new").hasAnyRole("ADMIN", "DOCTOR", "USER")
                .requestMatchers("/appointment/{hospitalId}/save").hasAnyRole("ADMIN", "DOCTOR", "USER")
                .requestMatchers("/appointment/{hospitalId}/{id}/edit").hasAnyRole("ADMIN", "DOCTOR", "USER")
                .requestMatchers("/appointment/{hospitalId}/{id}/update").hasAnyRole("ADMIN", "DOCTOR", "USER")
                .requestMatchers("/appointment/{hospitalId}/{id}/delete").hasAnyRole("ADMIN", "DOCTOR", "USER")
                .and()
                .formLogin()
                .defaultSuccessUrl("/hospital")
                .permitAll();


        return http.build();
    }

}
