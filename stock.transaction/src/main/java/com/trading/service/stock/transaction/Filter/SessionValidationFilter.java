package com.trading.service.stock.transaction.filter;

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
        log.info("Stock Transaction service: Validating user session");

        if (request.getSession().getAttribute("userName") == null
                        ||
                (request.getSession().getAttribute("role").equals("ADMIN") && request.getRequestURL().toString().contains("user"))
                        ||
                (request.getSession().getAttribute("role").equals("USER") && request.getRequestURL().toString().contains("admin"))
           )
        {
            log.warn("Stock Transaction service: Invalid session");
            response.setStatus(401);
            return;
        }
        chain.doFilter(request, response);
    }
}
