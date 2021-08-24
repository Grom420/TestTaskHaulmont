package com.example.demo.entitys;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Payment {

    @Id
    @Column(name = "ID_payment")
    @GeneratedValue
    private UUID idPayment;

    @Column(name = "payment_UUID")
    private String paymentUUID;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_credit_offer")
    private CreditOffer creditOffer;

    @Column(name = "date_of_pay")
    @NotNull
    private LocalDate dateOfPay;

    @Column(name = "all_sum")
    @NotNull
    private long allSum;

    @Column(name = "sum_of_body")
    @NotNull
    private long sumOfBody;

    @Column(name = "sum_of_percent")
    @NotNull
    private long sumOfPercent;

    @Column(name = "cash_balance")
    @NotNull
    private long cashBalance;

    public Payment(){}

    public Payment(LocalDate dateOfPay, long allSum, long sumOfBody, long sumOfPercent, long cashBalance) {
        this.idPayment = UUID.randomUUID();
        this.paymentUUID = this.idPayment.toString();
        this.dateOfPay = dateOfPay;
        this.allSum = allSum;
        this.sumOfBody = sumOfBody;
        this.sumOfPercent = sumOfPercent;
        this.cashBalance = cashBalance;
    }

    public long getCashBalance() {
        return cashBalance;
    }

    public void setCashBalance(long cashBalance) {
        this.cashBalance = cashBalance;
    }

    public UUID getIdPayment() {
        return idPayment;
    }

    public void setIdPayment(UUID idPayment) {
        this.idPayment = idPayment;
    }

    public String getPaymentUUID() {
        return paymentUUID;
    }

    public void setPaymentUUID(String paymentUUID) {
        this.paymentUUID = paymentUUID;
    }

    public CreditOffer getCreditOffer() {
        return creditOffer;
    }

    public void setCreditOffer(CreditOffer creditOffer) {
        this.creditOffer = creditOffer;
    }

    public LocalDate getDateOfPay() {
        return dateOfPay;
    }

    public void setDateOfPay(LocalDate dateOfPay) {
        this.dateOfPay = dateOfPay;
    }

    public long getAllSum() {
        return allSum;
    }

    public void setAllSum(long allSum) {
        this.allSum = allSum;
    }

    public long getSumOfBody() {
        return sumOfBody;
    }

    public void setSumOfBody(long sumOfBody) {
        this.sumOfBody = sumOfBody;
    }

    public long getSumOfPercent() {
        return sumOfPercent;
    }

    public void setSumOfPercent(long sumOfPercent) {
        this.sumOfPercent = sumOfPercent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return allSum == payment.allSum && sumOfBody == payment.sumOfBody && sumOfPercent == payment.sumOfPercent && cashBalance == payment.cashBalance && idPayment.equals(payment.idPayment) && paymentUUID.equals(payment.paymentUUID) && Objects.equals(creditOffer, payment.creditOffer) && Objects.equals(dateOfPay, payment.dateOfPay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPayment, paymentUUID, creditOffer, dateOfPay, allSum, sumOfBody, sumOfPercent, cashBalance);
    }
}
