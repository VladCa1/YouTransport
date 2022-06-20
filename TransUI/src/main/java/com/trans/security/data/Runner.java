package com.trans.security.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner{

	@Autowired
	UserRepository repository;

	@Override
	public void run(String... args) throws Exception {


	}

}
