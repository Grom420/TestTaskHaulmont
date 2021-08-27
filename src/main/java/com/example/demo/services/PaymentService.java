package com.example.demo.services;

import com.example.demo.entitys.Payment;

import java.util.List;

public interface PaymentService {
    List<Payment> findAll();
    void deleteAll();
}
