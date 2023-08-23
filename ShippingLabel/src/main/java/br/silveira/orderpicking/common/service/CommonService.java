package br.silveira.orderpicking.common.service;

import br.silveira.orderpicking.auth.security.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;

public class CommonService {

    public String getLoggedUser(){
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUsername();
    }

    public Long getCompanyId(){
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getCompanyId();
    }
}
