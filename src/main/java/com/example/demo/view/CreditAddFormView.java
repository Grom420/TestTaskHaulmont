package com.example.demo.view;

import com.example.demo.entitys.Credit;
import com.example.demo.services.CreditServiceImpl;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;

public class CreditAddFormView extends VerticalLayout {

    private CreditView creditView;
    private CreditServiceImpl creditServiceImpl;

    private TextField creditName = new TextField("Credit name");
    private TextField creditLimit = new TextField("Credit limit");
    private TextField interestRate = new TextField("Interest rate");

    public CreditAddFormView(CreditView creditView, CreditServiceImpl creditServiceImpl){
        this.creditView = creditView;
        this.creditServiceImpl = creditServiceImpl;

        HorizontalLayout layout = new HorizontalLayout(creditName, creditLimit, interestRate);
        add(layout);
        Button add = new Button("Add credit", e -> addCredit());
        add(add);
    }

    private void addCredit() {
        if(isEmptyFields()){
            Notification.show("Please enter all details");
        } else {
            Credit credit = new Credit(Long.parseLong(creditLimit.getValue()), Integer.parseInt(interestRate.getValue()), creditName.getValue());
            creditServiceImpl.addCredit(credit);
            creditView.updateList();
        }
    }

    private boolean isEmptyFields(){
        return creditLimit.getValue().equals("") && interestRate.getValue().equals("")
                && creditName.getValue().equals("");
    }
}
