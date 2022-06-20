package com.trans.services;

import java.util.List;

import com.trans.models.agent.CAgent;
import com.trans.models.agent.FTPAgent;
import com.trans.serviceInterface.models.AgentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trans.models.agent.Agent;
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
	public Long registerAgent(AgentDTO agent, String type) {
		Agent newAgent;
		if(type.equals("Transport")){
			newAgent = new FTPAgent();
		}else{
			newAgent = new CAgent();
		}
		newAgent.setEmail(agent.getEmail());
		newAgent.setCompanyInfo(agent.getCompanyInfo());
		newAgent.setFirstName(agent.getFirstName());
		newAgent.setLastName(agent.getLastName());
		newAgent.setToken(agent.getToken());

		agentDAO.save(newAgent);
		return newAgent.getId();
	}

	@Override
	public Agent findByToken(String token) {
		return agentDAO.findByToken(token);
	}


}
