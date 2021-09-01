package com.example.demo.view;
import com.example.demo.entitys.Client;
import com.example.demo.services.ClientServiceImpl;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.data.validator.RegexpValidator;

public class ClientAddFormView extends VerticalLayout {

    private ClientView clientView;
    private ClientServiceImpl clientServiceImpl;
    private Binder<Client> binder = new Binder<>(Client.class);

    private TextField name = new TextField("name");
    private TextField surname = new TextField("surname");
    private TextField lastname = new TextField("lastname");
    private TextField email = new TextField("email");
    private TextField number = new TextField("number");
    private TextField passport = new TextField("passport");

    private Button addClient = new Button("Add client");

    public ClientAddFormView(ClientView clientView, ClientServiceImpl clientServiceImpl){
        this.clientView = clientView;
        this.clientServiceImpl = clientServiceImpl;
        setHelperText();
        HorizontalLayout layout = new HorizontalLayout(name, surname, lastname, email, number, passport);

        add(layout);
        add(addClient);
        RegexpValidator nameValidator = new RegexpValidator("Incorrect data","(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$");
        RegexpValidator phoneValidator = new RegexpValidator("Incorrect phone number", "([+]{1}[0-9]{11})$");
        RegexpValidator passportValidator = new RegexpValidator("Incorrect passport", "^([0-9]{2}\\s{1}[0-9]{2}\\s{1}[0-9]{6})?$");
        binder.forField(name).withValidator(nameValidator).bind(Client::getName, Client::setName);
        binder.forField(surname).withValidator(nameValidator).bind(Client::getSurname, Client::setSurname);
        binder.forField(lastname).withValidator(nameValidator).bind(Client::getLastname, Client::setLastname);
        binder.forField(email).withValidator(new EmailValidator("Incorrect email address")).bind(Client::getEmail, Client::setEmail);
        binder.forField(number).withValidator(phoneValidator).bind(Client::getNumber, Client::setNumber);
        binder.forField(passport).withValidator(passportValidator).bind(Client::getPassport, Client::setPassport);
        addClient.addClickListener(event -> addClient());
    }

    private void setHelperText(){
        name.setHelperText("Any name containing English letters");
        surname.setHelperText("Any name containing English letters");
        lastname.setHelperText("Any name containing English letters");
        email.setHelperText("Correct sample: prefix@postfix.country");
        number.setHelperText("Correct sample: + and eleven digits");
        passport.setHelperText("Correct sample: XX XX XXXXXX");
    }

    private void addClient() {
        if (isEmptyFields()) {
            Notification.show("Please enter all details").setPosition(Notification.Position.MIDDLE);
        } else if (!binder.isValid()) {
            Notification.show("Please enter correct data").setPosition(Notification.Position.MIDDLE);
        } else {
            Client client = new Client(name.getValue(), surname.getValue(), lastname.getValue(), email.getValue(), number.getValue(), passport.getValue());
            clientServiceImpl.addClient(client);
            clientView.updateList();
        }
    }

    private boolean isEmptyFields(){
        return name.getValue().equals("") || surname.getValue().equals("")
                || lastname.getValue().equals("") || email.getValue().equals("") || number.getValue().equals("") || passport.getValue().equals("");
    }
}
