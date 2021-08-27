package com.example.demo.services;

import com.example.demo.entitys.Client;

import java.util.List;

public interface ClientService {
    List<Client> findAll();
    boolean addClient(Client client);
    void updateClient(Client client);
    boolean removeClient(Client client);
    boolean isOffer(Client client);
}

