package com.example.demo.entitys;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "client")
public class Client {

    @Id
    @Column(name = "ID_client")
    @GeneratedValue
    private UUID idClient;

    @Column(name = "client_UUID")
    private String clientUUID;

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<CreditOffer> creditOffers;

    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "email")
    private String email;
    @Column(name = "number")
    private String number;
    @Column(name = "passport")
    @NotNull
    private String passport;

    public Client() {

    }

    public Client(String name, String surname, String lastname, String email, String number, String passport) {
        this.idClient = UUID.randomUUID();
        this.clientUUID = this.idClient.toString();
        this.name = name;
        this.surname = surname;
        this.lastname = lastname;
        this.email = email;
        this.number = number;
        this.passport = passport;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public Set<CreditOffer> getCreditOffers() {
        return creditOffers;
    }

    public void setCreditOffers(Set<CreditOffer> creditOffers) {
        this.creditOffers = creditOffers;
    }

    public String getClientUUID() {
        return clientUUID;
    }

    public void setClientUUID(String clientUUID) {
        this.clientUUID = clientUUID;
    }

    public void setIdClient(UUID id_client) {
        this.idClient = id_client;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return idClient.equals(client.idClient) && name.equals(client.name) && surname.equals(client.surname) && lastname.equals(client.lastname) && email.equals(client.email) && number.equals(client.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idClient, name, surname, lastname, email, number);
    }
}

