package com.example.demo.repos;

import com.example.demo.entitys.Client;
import com.example.demo.entitys.Credit;
import com.example.demo.entitys.CreditOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CreditOfferRepos extends JpaRepository<CreditOffer, UUID> {
    CreditOffer findByOfferUUID(String offerUUID);
    CreditOffer findByClient(Client client);
    CreditOffer findByCredit(Credit credit);
}
