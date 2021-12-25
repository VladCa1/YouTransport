package com.trans.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trans.models.agent.Agent;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long>{

	
}
