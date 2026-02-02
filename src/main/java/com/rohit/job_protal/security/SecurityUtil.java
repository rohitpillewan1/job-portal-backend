package com.rohit.job_protal.security;

import com.rohit.job_protal.entity.User;
import com.rohit.job_protal.exception.UnauthenticatedUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Component
public class SecurityUtil {
    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null || !authentication.isAuthenticated()){
            throw new UnauthenticatedUser("Unauthenticated user");
        }

        Object principal = authentication.getPrincipal();
        if(!(principal instanceof  User)){
            throw new UnauthenticatedUser("Invalid user principal");
        }
        return (User)principal;
    }
}
