package com.vozni.springbootjwt.security;

import com.vozni.springbootjwt.model.UserEntity;
import com.vozni.springbootjwt.service.AuthServiceImpl;
import com.vozni.springbootjwt.util.UserUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final AuthServiceImpl authService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.length()>7){
            String token = bearerToken.substring(7);
            try {
                UserEntity user = authService.getUser(token);

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(user.getUsername(),null, UserUtils.getGrantedAuthorities(user));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }catch (Exception e){
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
            }

        }
        filterChain.doFilter(request,response);
    }
}
