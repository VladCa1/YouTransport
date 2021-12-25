package com.trans.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trans.models.characteristic.Property;

public interface PropertyRepository extends JpaRepository<Property, Long>{

}
