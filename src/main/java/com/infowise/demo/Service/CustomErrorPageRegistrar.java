package com.infowise.demo.Service;


import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.http.HttpStatus;

public class CustomErrorPageRegistrar implements ErrorPageRegistrar {
    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        ErrorPage error400 = new ErrorPage(HttpStatus.BAD_REQUEST, "error/400");
        ErrorPage error403 = new ErrorPage(HttpStatus.FORBIDDEN, "error/403");
        ErrorPage error500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "error/500");
        ErrorPage error503 = new ErrorPage(HttpStatus.SERVICE_UNAVAILABLE, "error/503");
        ErrorPage error404 = new ErrorPage(HttpStatus.NOT_FOUND, "error/404");

        registry.addErrorPages(error400, error403, error500, error503, error404);
    }
}