package com.trans.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.trans.security.data.MyUserDetailsService;
import com.trans.security.data.MyUserPrincipal;
import com.vaadin.flow.server.HandlerHelper.RequestType;
import com.vaadin.flow.shared.ApplicationConstants;
import com.vaadin.flow.spring.annotation.SpringComponent;

public final class SecurityUtils {
	
	public static final String ROLE_GOODS = "ROLE_Goods";
	public static final String ROLE_TRANSPORT = "ROLE_Transport";
		
    private SecurityUtils() {
        // Util methods only
    }

    public static boolean isFrameworkInternalRequest(HttpServletRequest request) { 
        final String parameterValue = request.getParameter(ApplicationConstants.REQUEST_TYPE_PARAMETER);
        return parameterValue != null
            && Stream.of(RequestType.values())
            .anyMatch(r -> r.getIdentifier().equals(parameterValue));
    }

    public static boolean isUserLoggedIn() { 
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null
            && !(authentication instanceof AnonymousAuthenticationToken)
            && authentication.isAuthenticated();
    }
    
    public static Collection<String> getUserRoles() {
    	Collection<String> roles = new ArrayList<>();
    	SecurityContextHolder.getContext().getAuthentication().getAuthorities().forEach(role ->{
    		roles.add(role.getAuthority());
    	});
    	return roles;
    }
    
    public static String getUserToken() {
    	 SecurityContext context = SecurityContextHolder.getContext();
         Object principal = context.getAuthentication().getPrincipal();
         if (principal instanceof MyUserPrincipal) {
            return ((MyUserPrincipal)principal).getUserToken();
         }else {
        	 return null;
         }
    }
}