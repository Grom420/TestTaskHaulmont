package com.example.demo.frontend;

import com.example.demo.view.ClientView;
import com.example.demo.entitys.Client;
import com.example.demo.services.ClientServiceImpl;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.data.validator.RegexpValidator;

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
        Client client = new Client();
        RegexpValidator nameValidator = new RegexpValidator("Incorrect data","(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$");
        RegexpValidator phoneValidator = new RegexpValidator("Incorrect phone number", "([+]{1}[0-9]{11})$");
        RegexpValidator passportValidator = new RegexpValidator("Incorrect passport", "^([0-9]{2}\\s{1}[0-9]{2}\\s{1}[0-9]{6})?$");
        binder.forField(name).withValidator(nameValidator).bind(Client::getName, Client::setName);
        binder.forField(surname).withValidator(nameValidator).bind(Client::getSurname, Client::setSurname);
        binder.forField(lastname).withValidator(nameValidator).bind(Client::getLastname, Client::setLastname);
        binder.forField(email).withValidator(new EmailValidator("Incorrect email address")).bind(Client::getEmail, Client::setEmail);
        binder.forField(number).withValidator(phoneValidator).bind(Client::getNumber, Client::setNumber);
        binder.forField(passport).withValidator(passportValidator).bind(Client::getPassport, Client::setPassport);
        binder.readBean(client);
        update.addClickListener(event -> update());
        delete.addClickListener(event -> delete());
    }

    private void update() {
        if(!binder.isValid()){
            Notification.show("Please enter correct data").setPosition(Notification.Position.MIDDLE);
        } else {
            Client client = binder.getBean();
            service.updateClient(client);
            clientView.updateList();
            setClient(null);
        }
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
