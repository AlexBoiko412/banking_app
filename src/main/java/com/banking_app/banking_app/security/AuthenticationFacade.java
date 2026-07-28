package com.banking_app.banking_app.security;

import com.banking_app.banking_app.security.interfaces.IAuthenticationFacade;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Component
public class AuthenticationFacade implements IAuthenticationFacade {
    public String getUsername(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
