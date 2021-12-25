package com.trans.serviceInterface.models;

import lombok.Getter;
import lombok.Setter;

public class AgentDTO {
	
	@Getter
	@Setter
	private String token;
	
	@Getter
	@Setter
	private String email;
	
	@Getter
	@Setter
	private String firstName;
	
	@Getter
	@Setter
	private String lastName;
	
	@Getter
	@Setter
	private String companyInfo;
	
	
	public AgentDTO() {
		
	}

	public AgentDTO(String token, String email, String firstName, String lastName, String companyInfo) {
		super();
		this.token = token;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.companyInfo = companyInfo;
	}
}
