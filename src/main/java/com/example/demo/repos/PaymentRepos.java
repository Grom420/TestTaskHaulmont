package com.example.demo.repos;

import com.example.demo.entitys.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentRepos extends JpaRepository<Payment, UUID> {
    void deleteAllInBatch();
}
