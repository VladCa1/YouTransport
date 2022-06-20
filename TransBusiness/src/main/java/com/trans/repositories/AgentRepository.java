package com.trans.repositories;

import com.trans.models.agent.FTPAgent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trans.models.agent.Agent;
import com.trans.models.agent.CAgent;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long>{

	Agent findByToken(String token);

}
