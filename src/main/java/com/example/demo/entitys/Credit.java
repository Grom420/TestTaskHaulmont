package com.example.demo.entitys;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "credit")
public class Credit {

    @Id
    @Column(name = "ID_credit")
    @GeneratedValue
    private UUID id_credit;

    @OneToMany(mappedBy = "credit", fetch = FetchType.EAGER)
    private Set<CreditOffer> creditOffers;

    @Column(name = "credit_UUID")
    private String creditUUID;

    @Column(name = "credit_limit")
    private long creditLimit;

    @Column(name = "credit_name")
    private String creditName;

    @Column(name = "interest_rate")
    private long interestRate;

    public Credit() {

    }

    public Credit(long creditLimit, int interestRate, String creditName) {
        this.id_credit = UUID.randomUUID();
        this.creditUUID = this.id_credit.toString();
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
        this.creditName = creditName;
    }

    public String getCreditName() {
        return creditName;
    }

    public void setCreditName(String creditName) {
        this.creditName = creditName;
    }

    public Set<CreditOffer> getCreditOffers() {
        return creditOffers;
    }

    public void setCreditOffers(Set<CreditOffer> creditOffers) {
        this.creditOffers = creditOffers;
    }

    public String getCreditUUID() {
        return creditUUID;
    }

    public void setCreditUUID(String creditUUID) {
        this.creditUUID = creditUUID;
    }

    public UUID getId_credit() {
        return id_credit;
    }

    public void setId_credit(UUID id_credit) {
        this.id_credit = id_credit;
    }

    public long getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(long creditLimit) {
        this.creditLimit = creditLimit;
    }

    public long getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(long interestRate) {
        this.interestRate = interestRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Credit credit = (Credit) o;
        return creditLimit == credit.creditLimit && interestRate == credit.interestRate && id_credit.equals(credit.id_credit) && creditUUID.equals(credit.creditUUID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_credit, creditUUID, creditLimit, interestRate);
    }
}
