package com.example.demo;

import com.example.demo.entitys.Client;
import com.example.demo.entitys.Credit;
import com.example.demo.repos.ClientRepos;
import com.example.demo.repos.CreditRepos;
import com.example.demo.services.ClientServiceImpl;
import com.example.demo.services.CreditServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class ApplicationTests {

    @Autowired
    ClientRepos clientRepos;

    @Autowired
    ClientServiceImpl clientService;

    @Autowired
    CreditRepos creditRepos;

    @Autowired
    CreditServiceImpl creditService;

    @Test
    void ClientAddAndRemove() {
        Client client = new Client("Name", "Surname", "Lastname", "email", "3134213542", "32523");
        clientService.addClient(client);
        Client potentialClient = clientRepos.findByClientUUID(client.getClientUUID());
        Assertions.assertEquals(client.getClientUUID(), potentialClient.getClientUUID());
        boolean isDeleted = clientService.removeClient(client);
        Client removedClient = clientRepos.findByClientUUID(client.getClientUUID());
        Assertions.assertTrue(isDeleted);
        Assertions.assertNull(removedClient);
    }

    @Test
    void CreditAddAndRemove() {
        Credit credit = new Credit(200000, 20, "Test name");
        creditService.addCredit(credit);
        Credit potentialCredit = creditRepos.findByCreditUUID(credit.getCreditUUID());
        Assertions.assertEquals(credit.getCreditUUID(), potentialCredit.getCreditUUID());
        boolean isDeleted = creditService.removeCredit(credit);
        Credit removedCredit = creditRepos.findByCreditUUID(credit.getCreditUUID());
        Assertions.assertTrue(isDeleted);
        Assertions.assertNull(removedCredit);
    }
}
