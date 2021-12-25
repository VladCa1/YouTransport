package com.trans.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trans.models.route.Location;

public interface LocationRepository extends JpaRepository<Location, Long>{

}
