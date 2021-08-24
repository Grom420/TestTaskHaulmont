package com.example.demo;

import com.example.demo.entitys.Credit;
import com.example.demo.repos.CreditRepos;
import com.example.demo.services.CreditServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class CreditTest {

    @Autowired
    CreditRepos creditRepos;

    @Autowired
    CreditServiceImpl creditService;

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
