package com.raf.example.HotelNotificationService.security;

import com.raf.example.HotelNotificationService.security.service.TokenService;
import io.jsonwebtoken.Claims;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Configuration
public class SecurityAspect {

    @Value("${oauth.jwt.secret}")
    private String jwtSecret;

    private TokenService tokenService;

    public SecurityAspect(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Around("@annotation(com.raf.example.HotelNotificationService.security.CheckSecurity)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        //Get method signature
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        //Check for authorization parameter
        String token = null;
        for (int i = 0; i < methodSignature.getParameterNames().length; i++) {
            if (methodSignature.getParameterNames()[i].equals("authorization")) {
                //Check bearer schema
                if (joinPoint.getArgs()[i].toString().startsWith("token")) {
                    //Get token
                    token = joinPoint.getArgs()[i].toString().split(" ")[1];
                }
            }
        }
        //If token is not presents return UNAUTHORIZED response
        if (token == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        //Try to parse token
        Claims claims = tokenService.parseToken(token);
        //If fails return UNAUTHORIZED response
        if (claims == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        //Check user role and proceed if user has appropriate role for specified route
        CheckSecurity checkSecurity = method.getAnnotation(CheckSecurity.class);
        String role = claims.get("role", String.class);
        if (Arrays.asList(checkSecurity.roles()).contains(role)) {
            return joinPoint.proceed();
        }
        //Return FORBIDDEN if user has't appropriate role for specified route
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    public Long getUserId(String authorization) {
        String token = authorization.split(" ")[1];
        Claims claims = tokenService.parseToken(token);
        return claims.get("id", Integer.class).longValue();
    }

    public String getUserEmail(String authorization) {
        String token = authorization.split(" ")[1];
        Claims claims = tokenService.parseToken(token);
        return claims.get("email", String.class);
    }

    public String getUserRole(String authorization) {
        String token = authorization.split(" ")[1];
        Claims claims = tokenService.parseToken(token);
        return claims.get("role", String.class);
    }

}