package com.trans.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trans.models.agent.Agent;
import com.trans.models.agent.CAgent;
import com.trans.repositories.AgentRepository;

@Service
@Transactional
public class AgentServiceImpl implements AgentService{

	@Autowired
	AgentRepository agentDAO;
	
	@Override
	public List<Agent> findAll() {
		return agentDAO.findAll();
	}

	@Override
	public Long registerAgent(Agent agent) {
		agentDAO.save(agent);
		return agent.getId();
	}

	@Override
	public CAgent findByToken(String token) {
		return agentDAO.findByToken(token);
	}


}
