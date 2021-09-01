package com.example.demo.frontend;

import com.example.demo.view.CreditView;
import com.example.demo.entitys.Credit;
import com.example.demo.services.CreditServiceImpl;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.converter.StringToLongConverter;
import com.vaadin.flow.data.validator.RegexpValidator;
import com.vaadin.flow.data.value.ValueChangeMode;

public class CreditEdit extends FormLayout {

    private CreditServiceImpl creditServiceImpl;

    private TextField creditLimit = new TextField("Credit limit");
    private TextField interestRate = new TextField("Interest rate");
    private TextField creditName = new TextField("Credit name");

    private Button update = new Button("Save");
    private Button delete = new Button("Delete");

    private Binder<Credit> binder = new Binder<>(Credit.class);

    private CreditView creditView;

    public CreditEdit(CreditView creditView, CreditServiceImpl creditServiceImpl){
        this.creditServiceImpl = creditServiceImpl;
        this.creditView = creditView;
        Credit credit = new Credit();
        HorizontalLayout buttons = new HorizontalLayout(update, delete);
        update.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(creditName, creditLimit, interestRate, buttons);
        RegexpValidator creditNameValidator = new RegexpValidator("Incorrect data","(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$");
        RegexpValidator numValidator = new RegexpValidator("Incorrect number", "(\\d{1,3}(?:\\S*\\d{3})*)");
        binder.forField(creditLimit)
                .withValidator(numValidator)
                .withConverter(new StringToLongConverter("must be long"))
                .bind(Credit::getCreditLimit, Credit::setCreditLimit);
        binder.forField(interestRate)
                .withValidator(numValidator)
                .withConverter(new StringToLongConverter("must be long"))
                .bind(Credit::getInterestRate, Credit::setInterestRate);
        binder.forField(creditName)
                .withValidator(creditNameValidator)
                .bind(Credit::getCreditName, Credit::setCreditName);
        binder.readBean(credit);
        update.addClickListener(event -> update());
        delete.addClickListener(event -> delete());
    }

    private void delete() {
        Credit credit = binder.getBean();
        creditServiceImpl.removeCredit(credit);
        creditView.updateList();
        setCredit(null);
    }

    private void update() {
        if (!binder.isValid()){
            Notification.show("Please enter correct data").setPosition(Notification.Position.MIDDLE);
        } else {
            Credit credit = binder.getBean();
            creditServiceImpl.updateCredit(credit);
            creditView.updateList();
            setCredit(null);
        }
    }

    public void setCredit(Credit credit) {
        binder.setBean(credit);

        if (credit == null) {
            setVisible(false);
        } else {
            setVisible(true);
            creditLimit.focus();
        }
    }
}
