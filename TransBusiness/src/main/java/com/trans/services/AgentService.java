package com.trans.services;

import java.util.List;

import com.trans.models.agent.Agent;
import com.trans.models.agent.CAgent;

public interface AgentService {

	List<Agent> findAll();

	Long registerAgent(Agent agent);

	CAgent findByToken(String token);
	

}
