package com.example.demo.services;

import com.example.demo.entitys.Client;
import com.example.demo.entitys.CreditOffer;
import com.example.demo.repos.ClientRepos;
import com.example.demo.repos.CreditOfferRepos;
import com.vaadin.flow.component.notification.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ClientServiceImpl implements ClientService{

    @Autowired
    private ClientRepos clientRepos;

    @Autowired
    private CreditOfferRepos creditOfferRepos;

    public ClientServiceImpl(ClientRepos clientRepos) {
        this.clientRepos = clientRepos;
    }

    public List<Client> findAll(){
        return clientRepos.findAll();
    }

    public boolean addClient(Client client) {
        Client present = clientRepos.findByClientUUID(client.getClientUUID());
        if (present != null) {
            return false;
        } else {
            if (client.getPassport().equals("")){
                Notification.show("Please enter your passport details");
                return false;
            }
            clientRepos.save(client);
            return true;
        }
    }

    public void updateClient(Client client) {
        clientRepos.save(client);
    }

    public boolean removeClient(Client client){
        Client present = clientRepos.findByClientUUID(client.getClientUUID());
        if (isOffer(present)) {
            Notification.show("This client is in the credit offer").setPosition(Notification.Position.MIDDLE);
            return false;
        }
        clientRepos.delete(present);
        return true;
    }

    public boolean isOffer(Client client){
        return creditOfferRepos.findAll().stream().anyMatch(creditOffer -> creditOffer.getClientUUID()
                .equals(client.getClientUUID()));
    }
}
