package com.trans.services;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.trans.security.data.MyUserDetailsService;
import com.trans.security.data.User;
import com.trans.security.data.UserRepository;
import com.trans.serviceInterface.models.AgentDTO;
import com.vaadin.flow.component.UI;

import reactor.core.publisher.Mono;

@Service
public class RegistrationServiceImpl implements RegistrationService{

	@Autowired
	MyUserDetailsService userService;
	
	@Autowired
	UserRepository userRepository; 
	
	@Autowired
	AuthenticationManager authenticationManager;
	
    public void autoLogin(String username, String password) {
		UserDetails userDetails = userService.loadUserByUsername(username);
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				userDetails, password, userDetails.getAuthorities());

		authenticationManager.authenticate(usernamePasswordAuthenticationToken);

		if (usernamePasswordAuthenticationToken.isAuthenticated()) {
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		}
	}
    
    public boolean registerUser(User user) throws URISyntaxException {
    	WebClient client = WebClient.create();
    	User toRegister = new User(user.getUsername(), user.getPassword(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getUserType(), user.getCompanyInfo());

    	URI uri = null;
    	if(toRegister.getUserType().equalsIgnoreCase("Goods")) {
    		uri = new URI("localhost:9061/agent/register/customer");
    	}else if(toRegister.getUserType().equalsIgnoreCase("Transport")) {
    		uri = new URI("localhost:9061/agent/register/provider");
    	}
    	
		AgentDTO apiUser = new AgentDTO(toRegister.getToken(), toRegister.getEmail(), toRegister.getFirstName(),
				toRegister.getLastName(), toRegister.getCompanyInfo());
	
		ResponseEntity<String> response = client.post()
	    .uri(uri)
	    .contentType(MediaType.APPLICATION_JSON)
	    .accept(MediaType.APPLICATION_JSON)
	    .body(Mono.just(apiUser), AgentDTO.class)
	    .retrieve()
	    .toEntity(String.class)
	    .block();
		

		System.out.println(response.getStatusCodeValue());

		
    	userRepository.save(toRegister);
		
    	autoLogin(user.getUsername(), user.getPassword());
    	
    	UI.getCurrent().navigate("");
    	
    	
    	return true;
    }
    

	@Override
	public List<String> findAllEmails() {
		return userRepository.findAllEmails();
	}

	@Override
	public List<String> findAllUserNames() {
		return userRepository.findAllUserNames();
	}
	
}
