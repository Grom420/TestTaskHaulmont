package com.example.demo.view;

import com.example.demo.entitys.Credit;
import com.example.demo.services.CreditServiceImpl;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToLongConverter;
import com.vaadin.flow.data.validator.RegexpValidator;

public class CreditAddFormView extends VerticalLayout {

    private CreditView creditView;
    private CreditServiceImpl creditServiceImpl;
    private Binder<Credit> binder = new Binder<>(Credit.class);

    private TextField creditName = new TextField("Credit name");
    private TextField creditLimit = new TextField("Credit limit");
    private TextField interestRate = new TextField("Interest rate");

    public CreditAddFormView(CreditView creditView, CreditServiceImpl creditServiceImpl){
        this.creditView = creditView;
        this.creditServiceImpl = creditServiceImpl;
        setHelperText();
        HorizontalLayout layout = new HorizontalLayout(creditName, creditLimit, interestRate);
        add(layout);
        Button add = new Button("Add credit", e -> addCredit());
        add(add);
        RegexpValidator creditNameValidator = new RegexpValidator("Incorrect data","(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$");
        RegexpValidator numValidator = new RegexpValidator("Incorrect number", "[1-9][0-9]*");
        binder.forField(creditName).withValidator(creditNameValidator).bind(Credit::getCreditName, Credit::setCreditName);
        binder.forField(creditLimit)
                .withValidator(numValidator)
                .withConverter(new StringToLongConverter("must be long"))
                .bind(Credit::getCreditLimit, Credit::setCreditLimit);
        binder.forField(interestRate)
                .withValidator(numValidator)
                .withConverter(new StringToLongConverter("must be long"))
                .bind(Credit::getInterestRate, Credit::setInterestRate);
    }

    private void setHelperText(){
        creditName.setHelperText("Any name containing English letters");
        creditLimit.setHelperText("Number greater than zero");
        interestRate.setHelperText("Number greater than zero");
    }

    private void addCredit() {
        if (isEmptyFields()){
            Notification.show("Please enter all details").setPosition(Notification.Position.MIDDLE);
        } else if (!binder.isValid()){
            Notification.show("Please enter correct data").setPosition(Notification.Position.MIDDLE);
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
