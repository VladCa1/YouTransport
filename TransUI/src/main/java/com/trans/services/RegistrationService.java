package com.trans.services;

import java.net.URISyntaxException;
import java.util.List;

import com.trans.security.data.User;

public interface RegistrationService {

	List<String> findAllEmails();
	
	List<String> findAllUserNames();
	
	boolean registerUser(User user) throws URISyntaxException;
	
	void autoLogin(String username, String password);
	
}
