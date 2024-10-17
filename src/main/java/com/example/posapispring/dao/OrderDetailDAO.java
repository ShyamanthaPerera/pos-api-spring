package com.example.posapispring.dao;

import com.example.posapispring.entity.impl.OrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailDAO extends JpaRepository<OrderDetailEntity, String> {
}
