package com.infowise.demo.config;

import com.infowise.demo.Repository.MemberRepository;
import com.infowise.demo.dto.InfoWisePrincipal;
import com.infowise.demo.dto.MemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests(auth -> auth
                        .antMatchers("/", "/project/**", "/member/**",
                                "/work/**", "/profile/**", "/seat/**").authenticated() // 특정 URL 패턴에 대해서만 인증 요구
                        .anyRequest().permitAll() // 나머지 URL 패턴은 인증 요구하지 않음
                )
                .formLogin()
                    .loginPage("/login")            // 사용자 정의 로그인 페이지
                    .defaultSuccessUrl("/")            // 로그인 성공 후 이동 페이지
                    .failureUrl("/login.html?error=true")            // 로그인 실패 후 이동 페이지
                    .usernameParameter("email")            // 아이디 파라미터명 설정
                    .passwordParameter("pw")            // 패스워드 파라미터명 설정
                    .loginProcessingUrl("/loginOk")            // 로그인 Form Action Url
                    .successHandler(new CustomLoginSuccessHandler())		// 로그인 성공 후 핸들러
                    .failureHandler(new LoginFailureHandler())		// 로그인 실패 후 핸들러
                .and()
                .logout(logout -> logout.logoutSuccessUrl("/"))
                .logout((logout) -> logout.permitAll())
                .csrf().disable()
                .build();
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(MemberRepository memberRepository){
        return email -> memberRepository.findByEmail(email)
                .map(MemberDTO::fromEntity)
                .map(InfoWisePrincipal::fromDTO)
                .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다. - email:"+email));
    }
}
