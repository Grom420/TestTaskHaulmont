package com.example.demo.services;

import com.example.demo.entitys.Credit;
import com.example.demo.entitys.CreditOffer;
import com.example.demo.repos.CreditOfferRepos;
import com.example.demo.repos.CreditRepos;
import com.vaadin.flow.component.notification.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditServiceImpl implements CreditService{

    @Autowired
    CreditRepos creditRepos;

    @Autowired
    CreditOfferRepos creditOfferRepos;

    public CreditServiceImpl(CreditRepos creditRepos) {
        this.creditRepos = creditRepos;
    }

    public boolean addCredit(Credit credit) {
        Credit present = creditRepos.findByCreditUUID(credit.getCreditUUID());
        if (present != null) {
            return false;
        } else {
            creditRepos.save(credit);
            return true;
        }
    }

    public List<Credit> findAll() {
        return creditRepos.findAll();
    }

    public void updateCredit(Credit credit) {
        creditRepos.save(credit);
    }

    public boolean removeCredit(Credit credit){
        Credit present = creditRepos.findByCreditUUID(credit.getCreditUUID());
        if (isOffer(present)) {
            Notification.show("This credit is in the credit offer");
            return false;
        }
        creditRepos.delete(present);
        return true;
    }

    public boolean isOffer(Credit credit){
        return creditOfferRepos.findAll().stream().anyMatch(creditOffer -> creditOffer.getCreditUUID()
                .equals(credit.getCreditUUID()));
    }
}
