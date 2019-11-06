package de.otto.mytoys.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class ApiKeyFilter extends OncePerRequestFilter {

    private static final String API_KEY_HEADER = "x-api-key";
    private static final String API_KEY = "hz7JPdKK069Ui1TRxxd1k8BQcocSVDkj219DVzzD";

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws IOException, ServletException {
        String providedKey = httpServletRequest.getHeader(API_KEY_HEADER);
        if (providedKey == null) {
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else if (!providedKey.equals(API_KEY)) {
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }
}
