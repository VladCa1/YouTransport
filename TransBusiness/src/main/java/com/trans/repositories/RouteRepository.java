package com.trans.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trans.models.route.Route;

public interface RouteRepository extends JpaRepository<Route, Long>{

}
