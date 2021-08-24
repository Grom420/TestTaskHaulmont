package com.example.demo.view;

import com.example.demo.entitys.Client;
import com.example.demo.entitys.Credit;
import com.example.demo.entitys.CreditOffer;
import com.example.demo.services.ClientServiceImpl;
import com.example.demo.services.CreditOfferServiceImpl;
import com.example.demo.services.CreditServiceImpl;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CreditOfferAddFormView extends VerticalLayout {

    private CreditOfferView creditOfferView;
    private CreditOfferServiceImpl creditOfferServiceImpl;
    private ClientServiceImpl clientService;
    private CreditServiceImpl creditService;

    private TextField clientUUID = new TextField("Client UUID");
    private TextField creditUUID = new TextField("Credit UUID");
    private ComboBox<String> clientPassportLabel = new ComboBox<>("Client passport");
    private ComboBox<String> creditNameLabel = new ComboBox<>("Credit name");
    private TextField sumCredit = new TextField("Sum credit");
    private TextField creditTerm = new TextField("Credit term");

    public CreditOfferAddFormView(CreditOfferView creditOfferView,
                                  CreditOfferServiceImpl creditOfferServiceImpl,
                                  ClientServiceImpl clientService,
                                  CreditServiceImpl creditService){
        this.creditOfferView = creditOfferView;
        this.creditOfferServiceImpl = creditOfferServiceImpl;
        this.clientService = clientService;
        this.creditService = creditService;

        clientUUID.setReadOnly(true);
        creditUUID.setReadOnly(true);

        List<Client> clients = clientService.findAll();
        clientPassportLabel.setItems(clients
                .stream()
                .map(Client::getPassport)
                .collect(Collectors.toList()));
        add(clientPassportLabel);

        List<Credit> credits = creditService.findAll();
        creditNameLabel.setItems(credits
                .stream()
                .map(Credit::getCreditName)
                .collect(Collectors.toList()));
        add(creditNameLabel);

        HorizontalLayout layout = new HorizontalLayout(creditNameLabel, clientPassportLabel, sumCredit, creditTerm);
        add(layout);
        Button add = new Button("Add credit offer", clickEvent -> addCreditOffer());
        add(add);
    }


    private void addCreditOffer() {
        Credit credit = new Credit();
        credit.setCreditName(creditNameLabel.getValue());
        Client client = new Client();
        client.setPassport(clientPassportLabel.getValue());
        CreditOffer creditOffer = new CreditOffer(client, credit, Long.parseLong(sumCredit.getValue()), Integer.parseInt(creditTerm.getValue()));
        creditOfferServiceImpl.addCreditOffer(creditOffer);
        creditOfferView.updateList();
    }
}
