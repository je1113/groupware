package com.infowise.demo.config;

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        String errorMessage = "Invalid username or password";

        if (exception instanceof LockedException) {
            errorMessage = "Your account has been locked due to too many failed login attempts. Please contact the administrator.";
        } else if (exception instanceof DisabledException) {
            errorMessage = "Your account has been disabled. Please contact the administrator.";
        } else if (exception instanceof AccountExpiredException) {
            errorMessage = "Your account has expired. Please contact the administrator.";
        } else if (exception instanceof CredentialsExpiredException) {
            errorMessage = "Your credentials have expired. Please update your password.";
        }

        setDefaultFailureUrl("/login?error=true&message=" + errorMessage);

        super.onAuthenticationFailure(request, response, exception);
    }
}
