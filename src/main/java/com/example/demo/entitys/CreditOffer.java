package com.example.demo.entitys;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "credit_offer")
public class CreditOffer {

    @Id
    @Column(name = "ID_credit_offer")
    @GeneratedValue
    private UUID idCreditOffer;

    @Column(name = "credit_offer_UUID")
    private String offerUUID;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_client")
    private Client client;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_credit")
    private Credit credit;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_bank")
    private Bank bank;

    @OneToMany(mappedBy = "creditOffer", fetch = FetchType.EAGER)
    private Set<Payment> payments;

    @Column(name = "sum_credit")
    private long sumCredit;

    @Column(name = "credit_term")
    private int creditTerm;

    @Column(name = "sum_of_month")
    private long sumOfMonth;

    public CreditOffer() {

    }

    public CreditOffer(Client client, Credit credit, long sumCredit, int creditTerm) {
        this.idCreditOffer = UUID.randomUUID();
        this.offerUUID = this.idCreditOffer.toString();
        this.client = client;
        this.credit = credit;
        this.sumCredit = sumCredit;
        this.creditTerm = creditTerm;
    }

    public long getSumOfMonth() {
        return sumOfMonth;
    }

    public void setSumOfMonth(long sumOfMonth) {
        this.sumOfMonth = sumOfMonth;
    }

    public int getCreditTerm() {
        return creditTerm;
    }

    public void setCreditTerm(int creditTerm) {
        this.creditTerm = creditTerm;
    }

    public String getNameCredit(){
        return credit.getCreditName();
    }

    public String getNameClient(){
        return client.getName();
    }

    public String getClientUUID(){
        return this.client.getClientUUID();
    }

    public String getCreditUUID(){
        return this.credit.getCreditUUID();
    }

    public String getClientPassport(){
        return this.client.getPassport();
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public String getOfferUUID() {
        return offerUUID;
    }

    public void setOfferUUID(String offerUUID) {
        this.offerUUID = offerUUID;
    }

    public UUID getIdCreditOffer() {
        return idCreditOffer;
    }

    public void setIdCreditOffer(UUID idCreditOffer) {
        this.idCreditOffer = idCreditOffer;
    }

    public long getSumCredit() {
        return sumCredit;
    }

    public void setSumCredit(long sumCredit) {
        this.sumCredit = sumCredit;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Credit getCredit() {
        return credit;
    }

    public void setCredit(Credit credit) {
        this.credit = credit;
    }

    public Set<Payment> getPayments() {
        return payments;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditOffer that = (CreditOffer) o;
        return sumCredit == that.sumCredit && creditTerm == that.creditTerm && sumOfMonth == that.sumOfMonth && idCreditOffer.equals(that.idCreditOffer) && offerUUID.equals(that.offerUUID) && Objects.equals(client, that.client) && Objects.equals(credit, that.credit) && Objects.equals(bank, that.bank) && Objects.equals(payments, that.payments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCreditOffer, offerUUID, client, credit, bank, payments, sumCredit, creditTerm, sumOfMonth);
    }
}