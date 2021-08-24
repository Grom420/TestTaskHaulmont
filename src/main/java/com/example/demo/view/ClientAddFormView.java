package com.example.demo.view;
import com.example.demo.entitys.Client;
import com.example.demo.services.ClientServiceImpl;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;

public class ClientAddFormView extends VerticalLayout {

    private ClientView clientView;
    private ClientServiceImpl clientServiceImpl;

    private TextField UUID = new TextField("UUID");
    private TextField name = new TextField("name");
    private TextField surname = new TextField("surname");
    private TextField lastname = new TextField("lastname");
    private TextField email = new TextField("email");
    private TextField number = new TextField("number");
    private TextField passport = new TextField("passport");

    public ClientAddFormView(ClientView clientView, ClientServiceImpl clientServiceImpl){
        this.clientView = clientView;
        this.clientServiceImpl = clientServiceImpl;

        HorizontalLayout layout = new HorizontalLayout(name, surname, lastname, email, number, passport);

        add(layout);
        Button add = new Button("Add client", e -> addClient());
        add(add);
    }

    private void addClient(){
        if(isEmptyFields()) {
            Notification.show("Please enter all details");
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
