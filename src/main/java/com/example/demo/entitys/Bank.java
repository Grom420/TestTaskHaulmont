package com.example.demo.entitys;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "bank")
public class Bank {

    @Id
    @Column(name = "ID_bank")
    @GeneratedValue
    private UUID id_bank;

    @Column(name = "bank_UUID")
    private String bankUUID;

    @OneToMany(mappedBy = "bank", fetch = FetchType.EAGER)
    private Set<CreditOffer> creditOffers;

    public Bank() {
        this.id_bank = UUID.randomUUID();
        this.bankUUID = this.id_bank.toString();
    }

    public String getBankUUID() {
        return bankUUID;
    }

    public void setBankUUID(String bankUUID) {
        this.bankUUID = bankUUID;
    }

    public Set<CreditOffer> getCreditOffers() {
        return creditOffers;
    }

    public void setCreditOffers(Set<CreditOffer> creditOffers) {
        this.creditOffers = creditOffers;
    }

    public UUID getId_bank() {
        return id_bank;
    }

    public void setId_bank(UUID id_bank) {
        this.id_bank = id_bank;
    }


}
