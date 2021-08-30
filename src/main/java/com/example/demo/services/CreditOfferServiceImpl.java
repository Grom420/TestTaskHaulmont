package com.example.demo.services;

import com.example.demo.entitys.Client;
import com.example.demo.entitys.Credit;
import com.example.demo.entitys.CreditOffer;
import com.example.demo.entitys.Payment;
import com.example.demo.repos.ClientRepos;
import com.example.demo.repos.CreditOfferRepos;
import com.example.demo.repos.CreditRepos;
import com.example.demo.repos.PaymentRepos;
import com.vaadin.flow.component.notification.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class CreditOfferServiceImpl implements CreditOfferService{

    @Autowired
    private CreditOfferRepos creditOfferRepos;
    @Autowired
    private ClientRepos clientRepos;
    @Autowired
    private CreditRepos creditRepos;
    @Autowired
    private PaymentRepos paymentRepos;

    public List<CreditOffer> findAll() {
        return creditOfferRepos.findAll();
    }

    public boolean addCreditOffer(CreditOffer creditOffer) {
        Client client = clientRepos.findByPassport(creditOffer.getClient().getPassport());
        Credit credit = creditRepos.findByCreditName(creditOffer.getCredit().getCreditName());
        CreditOffer present = creditOfferRepos.findByOfferUUID(creditOffer.getOfferUUID());
        if (client != null && credit != null && present == null){
            if (creditOffer.getSumCredit() > credit.getCreditLimit()){
                Notification.show("The credit amount must not exceed the limit");
                return false; //проверка на лимит кредита
            }
            if (creditOffer.getSumCredit() < 50000){
                Notification.show("The credit amount is too small");
                return false; //проверка на малый кредит
            }
            creditOffer.setCredit(credit);
            creditOffer.setClient(client);
            creditOffer.setSumOfMonth(calculateSumOfMonth(creditOffer));
            if (isDuplicatedCreditOffer(client, credit)){
                Notification.show("Such a credit offer already exists");
                return false;
            }
            creditOfferRepos.save(creditOffer);
            return true;
        } else {
            return false;
        }
    }
    //Проверка на присутствие одного и того же клиента с одним и тем же кредитом
    private boolean isDuplicatedCreditOffer(Client client, Credit credit){
        return creditOfferRepos.findAll().stream().anyMatch(creditOffer -> (creditOffer.getClientUUID()
                .equals(client.getClientUUID())) && (creditOffer.getCreditUUID().equals(credit.getCreditUUID())));
    }

    public void updateCreditOffer(CreditOffer creditOffer) {
        creditOffer.setSumOfMonth(calculateSumOfMonth(creditOffer));
        creditOfferRepos.save(creditOffer);
    }

    public boolean removeCreditOffer(CreditOffer creditOffer) {
        CreditOffer present = creditOfferRepos.findByOfferUUID(creditOffer.getOfferUUID());
        if (present != null) {
            creditOfferRepos.delete(present);
            return true;
        } else {
            return false;
        }
    }

    public long calculateSumOfMonth(CreditOffer creditOffer){ //вычисление суммы за один месяц с учетом процентов
        double percent = creditOffer.getCredit().getInterestRate()/12.0/100.0;
        double sum = creditOffer.getSumCredit();
        double creditTerm = creditOffer.getCreditTerm();
        return Math.round(sum*(percent+(percent/(Math.pow((percent+1), creditTerm*12)-1))));
    }

    public void calculatePayment(CreditOffer creditOffer){
        double percent = creditOffer.getCredit().getInterestRate()/12.0/100.0;
        long paymentSum = calculateSumOfMonth(creditOffer); //ежемесечная оплата
        long sumOfCredit = creditOffer.getSumCredit(); //общая сумма для оплаты кредита
        long sumOfPercent; //сумма процентов по кредиту
        long sumOfBody; //сумма тела кредита
        LocalDate localDate = LocalDate.now();
        for (int i = 0; i < creditOffer.getCreditTerm()*12-1; i++) { //построение таблицы графика платежей
            sumOfPercent = Math.round(sumOfCredit*percent);
            sumOfBody = paymentSum - sumOfPercent;
            sumOfCredit = sumOfCredit - sumOfBody;
            localDate = localDate.plusMonths(1);
            paymentRepos.save(new Payment(localDate, paymentSum, sumOfBody, sumOfPercent, sumOfCredit));
        }
    }
}
