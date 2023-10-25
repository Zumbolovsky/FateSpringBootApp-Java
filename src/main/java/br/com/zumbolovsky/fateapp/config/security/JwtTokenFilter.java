package br.com.zumbolovsky.fateapp.config.security;

import br.com.zumbolovsky.fateapp.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final UserService userService;

    public JwtTokenFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        attemptAuthenticationFromToken(header, request);
        filterChain.doFilter(request, response);
    }

    private void attemptAuthenticationFromToken(String header, HttpServletRequest request) {
        if (!isEmpty(header) && header.startsWith("Bearer ")) {
            // Get jwt token and validate
            final String[] splittedHeader = header.split(" ");
            var token = splittedHeader[splittedHeader.length - 1];
            if (JwtToken.validate(token)) {
                // Get user identity and set it on the spring security context
                SecurityContextHolder.getContext().setAuthentication(getAuthentication(request, token));
            }
        }
    }

    private Authentication getAuthentication(HttpServletRequest request, String token) {
        return mapToUsernamePasswordAuthenticationToken(
                userService.loadUserByUsername(JwtToken.getUsername(token)),
                request
        );
    }

    private UsernamePasswordAuthenticationToken mapToUsernamePasswordAuthenticationToken(
        UserDetails userDetails, HttpServletRequest request) {
        final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return token;
    }
}