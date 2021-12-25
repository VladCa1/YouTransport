package com.trans.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trans.repositories.AgentRepository;

@Service
public class AuthenticationService {

	@Autowired
	AgentRepository agentDAO;
	

}
