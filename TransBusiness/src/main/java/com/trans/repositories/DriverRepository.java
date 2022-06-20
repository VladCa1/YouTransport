package com.trans.repositories;

import com.trans.models.transport.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {

    @Query("select driver from Driver driver where driver.token = :token")
    List<Driver> findAllByToken(String token);

    @Query("select driver.token from Driver driver where driver.id = :id")
    String getTokenById(Long id);

    @Query("delete from Driver where id = :id")
    @Modifying
    void deleteById(Long id);
}
