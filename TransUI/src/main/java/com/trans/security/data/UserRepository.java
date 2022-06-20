package com.trans.security.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    
    @Query("select au.email from applicationUsers au")
    List<String> findAllEmails();
    
    @Query("select au.username from applicationUsers au")
    List<String> findAllUserNames();

    User findByToken(String token);
    	
}