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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class CreditOfferServiceImpl implements CreditOfferService{

    private static final BigDecimal NUMBER_OF_MONTHS = new BigDecimal("12.0");
    private static final BigDecimal DECIMAL = new BigDecimal("100.0");
    private static final int PRECISION = 8;

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
                Notification.show("The credit amount must not exceed the limit").setPosition(Notification.Position.MIDDLE);
                return false; //проверка на лимит кредита
            }
            creditOffer.setCredit(credit);
            creditOffer.setClient(client);
            creditOffer.setSumOfMonth(calculateSumOfMonth(creditOffer));
            if (isDuplicatedCreditOffer(client, credit)){
                Notification.show("Such a credit offer already exists").setPosition(Notification.Position.MIDDLE);
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
        BigDecimal percent = new BigDecimal(creditOffer.getCredit().getInterestRate())
                .divide(NUMBER_OF_MONTHS, PRECISION, RoundingMode.CEILING)
                .divide(DECIMAL, PRECISION, RoundingMode.CEILING);
        long sumCredit = creditOffer.getSumCredit();
        int creditTermOfMonth = creditOffer.getCreditTerm()*NUMBER_OF_MONTHS.intValue();
        BigDecimal percentAddOne = percent.add(BigDecimal.ONE);
        BigDecimal denominator = percentAddOne.pow(creditTermOfMonth).subtract(BigDecimal.ONE);
        BigDecimal resultOfDivide = percent.divide(denominator, PRECISION, RoundingMode.CEILING);
        return percent.add(resultOfDivide).multiply(BigDecimal.valueOf(sumCredit)).longValue();
    }

    public void calculatePayment(CreditOffer creditOffer){
        BigDecimal percent = new BigDecimal(creditOffer.getCredit().getInterestRate())
                .divide(NUMBER_OF_MONTHS, PRECISION, RoundingMode.CEILING)
                .divide(DECIMAL, PRECISION, RoundingMode.CEILING);
        long paymentSum = calculateSumOfMonth(creditOffer); //ежемесечная оплата
        long sumOfCredit = creditOffer.getSumCredit(); //общая сумма для оплаты кредита
        long sumOfPercent; //сумма процентов по кредиту
        long sumOfBody; //сумма тела кредита
        LocalDate localDate = LocalDate.now();
        for (int i = 0; i < creditOffer.getCreditTerm()*NUMBER_OF_MONTHS.intValue()-1; i++) { //построение таблицы графика платежей
            sumOfPercent = percent.multiply(BigDecimal.valueOf(sumOfCredit)).longValue();
            sumOfBody = paymentSum - sumOfPercent;
            sumOfCredit = sumOfCredit - sumOfBody;
            localDate = localDate.plusMonths(1);
            paymentRepos.save(new Payment(localDate, paymentSum, sumOfBody, sumOfPercent, sumOfCredit));
        }
    }
}
