package com.trans.controllers;

import java.util.List;

import com.trans.serviceInterface.models.AgentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trans.models.agent.Agent;
import com.trans.models.agent.CAgent;
import com.trans.models.agent.FTPAgent;
import com.trans.services.AgentService;

@RestController
@RequestMapping("/agent")
public class AgentController {

	@Autowired
	private AgentService agentService;
	
	@GetMapping("/findAll")
    public List<Agent> findAll() {
        return agentService.findAll();
    }
	
	@PostMapping("/register/customer")
    public void registerCustomer(@RequestBody AgentDTO agent) {
//        if(agentService.registerAgent(agent) != null) {
//        	return new ResponseEntity<>(HttpStatus.OK);
//        }else {
//        	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
		agentService.registerAgent(agent, "Goods");
    }
	
	@PostMapping("/register/provider")
    public void registerProvider(@RequestBody AgentDTO agent) {
//        if(agentService.registerAgent(agent) != null) {
//        	return new ResponseEntity<>(HttpStatus.OK);
//        }else {
//        	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
		agentService.registerAgent(agent, "Transport");
    }
	
	
	
}
