package com.example.demo.services;

import com.example.demo.entitys.Client;
import com.example.demo.entitys.CreditOffer;

import java.util.List;

public interface CreditOfferService {

    List<CreditOffer> findAll();
    boolean addCreditOffer(CreditOffer creditOffer);
    void updateCreditOffer(CreditOffer creditOffer);
    void removeCreditOffer(CreditOffer creditOffer);
}
