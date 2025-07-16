package com.tofas.yky.config;

import com.tofas.core.common.security.TfAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TfAnonymousUserFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        TfAuthentication tfAuthentication = new TfAuthentication("D_MUHAMMEDEREND","",0);
        tfAuthentication.setAuthenticated(false);
        if(authentication == null){
            //response.sendError(403);
            authentication.setAuthenticated(false);
            SecurityContextHolder.getContext().setAuthentication(tfAuthentication);
        }
        filterChain.doFilter(request, response);
    }
}
