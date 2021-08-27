package com.example.demo.services;

import com.example.demo.entitys.Credit;

import java.util.List;

public interface CreditService {
    boolean addCredit(Credit credit);
    List<Credit> findAll();
    void updateCredit(Credit credit);
    boolean removeCredit(Credit credit);
    boolean isOffer(Credit credit);
}
