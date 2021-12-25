package com.trans.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trans.models.transport.TransportEntity;

public interface TransportEntityRepository extends JpaRepository<TransportEntity, Long>{

}
