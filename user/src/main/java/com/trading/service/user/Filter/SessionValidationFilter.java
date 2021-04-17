package com.trading.service.user.filter;

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
        log.info("User service: Validating user session. Inside " + this.getClass() + "."
                + Thread.currentThread().getStackTrace()[1].getMethodName() + " for userId: "+request.getSession().getAttribute("userName"));
        if (request.getSession().getAttribute("userName") == null &&
                ! request.getRequestURL().toString().contains("new-user")
        )
        {
            log.warn("User service: Invalid session. Request failed");
            response.setStatus(401);
            return;
        }

        chain.doFilter(request, response);
    }
}
