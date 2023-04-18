package com.infowise.demo.config;

import com.infowise.demo.Repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests(auth -> auth.anyRequest().permitAll())
                .formLogin().and()
                .csrf().disable()
                .build();
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    }
    public class CustomUserDetailsService implements UserDetailsService {

        @Autowired
        MemberRepository memberRepository;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            return null;//반환할 타입이 Member와 맞지 않는다.
        }
    }
}
