package com.trans.repositories;

import com.trans.models.characteristic.GoodsEntityProperty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsPropertyRepository extends JpaRepository<GoodsEntityProperty, Long> {
}
