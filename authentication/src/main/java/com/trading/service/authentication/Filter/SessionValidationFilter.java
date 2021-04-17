package com.trading.service.authentication.Filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class SessionValidationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        log.info("Authentication service: Validating user session. Inside " + this.getClass() + ".");
        int length= request.getRequestURL().length();

        if (request.getSession().getAttribute("userName") == null &&
                 request.getRequestURL().substring(length-6,length).toString().equals("logout"))
        {
            log.warn("Authentication service: Invalid session");
            response.setStatus(401);
            return;
        }

        chain.doFilter(request, response);
    }
}
