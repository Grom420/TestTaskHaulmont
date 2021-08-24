package com.example.demo.frontend;

import com.example.demo.view.CreditView;
import com.example.demo.entitys.Credit;
import com.example.demo.services.CreditServiceImpl;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.converter.StringToLongConverter;

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
        binder.forField(creditLimit)
                .withConverter(new StringToLongConverter("must be integer"))
                .bind(Credit::getCreditLimit, Credit::setCreditLimit);
        binder.readBean(credit);
        binder.forField(interestRate)
                .withConverter(new StringToIntegerConverter("must be integer"))
                .bind(Credit::getInterestRate, Credit::setInterestRate);
        binder.readBean(credit);
        binder.forField(creditName).bind(Credit::getCreditName, Credit::setCreditName);
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
        Credit credit = binder.getBean();
        creditServiceImpl.updateCredit(credit);
        creditView.updateList();
        setCredit(null);
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
