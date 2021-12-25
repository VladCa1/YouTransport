package com.trans.services;

import java.util.List;

import com.trans.models.agent.Agent;

public interface AgentService {

	List<Agent> findAll();

	Long registerAgent(Agent agent);
	

}
