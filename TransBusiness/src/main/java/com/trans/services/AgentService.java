package com.trans.services;

import java.util.List;

import com.trans.models.agent.Agent;
import com.trans.serviceInterface.models.AgentDTO;

public interface AgentService {

	List<Agent> findAll();

	Long registerAgent(AgentDTO agent, String type);

	Agent findByToken(String token);
	

}
