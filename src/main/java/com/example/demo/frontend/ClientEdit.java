package com.example.demo.frontend;

import com.example.demo.view.ClientView;
import com.example.demo.entitys.Client;
import com.example.demo.services.ClientServiceImpl;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class ClientEdit extends FormLayout {

    private final ClientServiceImpl service;

    private TextField name = new TextField("Firstname");
    private TextField lastname = new TextField("Lastname");
    private TextField surname = new TextField("Surname");
    private TextField email = new TextField("email");
    private TextField number = new TextField("number");
    private TextField passport = new TextField("passport");

    private Button update = new Button("Save");
    private Button delete = new Button("Delete");

    private Binder<Client> binder = new Binder<>(Client.class);

    private ClientView clientView;

    public ClientEdit(ClientView clientView, ClientServiceImpl clientServiceImpl) {
        this.service = clientServiceImpl;
        this.clientView = clientView;
        HorizontalLayout buttons = new HorizontalLayout(update, delete);
        update.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(name, lastname, surname, email, number, passport, buttons);
        binder.bindInstanceFields(this);
        update.addClickListener(event -> update());
        delete.addClickListener(event -> delete());
    }

    private void update() {
        Client client = binder.getBean();
        service.updateClient(client);
        clientView.updateList();
        setClient(null);
    }

    private void delete() {
        Client Client = binder.getBean();
        service.removeClient(Client);
        clientView.updateList();
        setClient(null);
    }

    public void setClient(Client client) {
        binder.setBean(client);

        if (client == null) {
            setVisible(false);
        } else {
            setVisible(true);
            name.focus();
        }
    }
}
