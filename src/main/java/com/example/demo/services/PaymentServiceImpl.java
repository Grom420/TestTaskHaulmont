package com.example.demo.services;

import com.example.demo.entitys.Credit;
import com.example.demo.entitys.CreditOffer;
import com.example.demo.entitys.Payment;
import com.example.demo.repos.PaymentRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    private PaymentRepos paymentRepos;

    @Override
    public List<Payment> findAll() {
        return paymentRepos.findAll();
    }

    public void deleteAll(){
        paymentRepos.deleteAllInBatch();
    }
}
