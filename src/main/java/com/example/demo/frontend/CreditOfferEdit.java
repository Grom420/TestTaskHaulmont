package com.example.demo.frontend;

import com.example.demo.entitys.CreditOffer;
import com.example.demo.services.CreditOfferServiceImpl;
import com.example.demo.services.PaymentServiceImpl;
import com.example.demo.view.CreditOfferView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.converter.StringToLongConverter;
import com.vaadin.flow.data.validator.RegexpValidator;

public class CreditOfferEdit extends FormLayout {

    private CreditOfferServiceImpl creditOfferService;
    private PaymentServiceImpl paymentService;

    private TextField sumCredit = new TextField("Sum credit");
    private TextField sumOfMonth = new TextField("Sum of month");
    private TextField creditTerm = new TextField("Credit term");

    private Button update = new Button("Save");
    private Button delete = new Button("Delete");
    private Button getPayment = new Button("Get a payment");

    private Binder<CreditOffer> binder = new Binder<>(CreditOffer.class);

    private CreditOfferView creditOfferView;
    public CreditOfferEdit(CreditOfferView creditOfferView, CreditOfferServiceImpl creditOfferService, PaymentServiceImpl paymentService){
        this.paymentService = paymentService;
        this.creditOfferService = creditOfferService;
        this.creditOfferView = creditOfferView;
        HorizontalLayout buttons = new HorizontalLayout(update, delete, getPayment);
        update.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(sumCredit, buttons);
        CreditOffer creditOffer = new CreditOffer();
        RegexpValidator numValidator = new RegexpValidator("Incorrect number", "(\\d{1,3}(?:\\S*\\d{3})*)");
        binder.forField(sumCredit)
                .withValidator(numValidator)
                .withConverter(new StringToLongConverter("must be long"))
                .bind(CreditOffer::getSumCredit, CreditOffer::setSumCredit);
        binder.forField(sumOfMonth)
                .withConverter(new StringToLongConverter("must be long"))
                .bind(CreditOffer::getSumOfMonth, CreditOffer::setSumOfMonth);
        binder.forField(creditTerm)
                .withConverter(new StringToIntegerConverter("must be integer"))
                .bind(CreditOffer::getCreditTerm, CreditOffer::setCreditTerm);
        binder.readBean(creditOffer);
        getPayment.addClickListener(event -> getCalculatePayment());
        update.addClickListener(event -> update());
        delete.addClickListener(event -> delete());
    }

    private void getCalculatePayment(){
        paymentService.deleteAll();
        CreditOffer creditOffer = binder.getBean();
        creditOfferService.calculatePayment(creditOffer);
        creditOfferView.updateList();
        setCreditOffer(null);
    }

    private void delete() {
        CreditOffer creditOffer = binder.getBean();
        creditOfferService.removeCreditOffer(creditOffer);
        creditOfferView.updateList();
        setCreditOffer(null);
    }

    private void update() {
        if(!binder.isValid()){
            Notification.show("Incorrect data").setPosition(Notification.Position.MIDDLE);
        } else if (binder.getBean().getSumCredit() >= binder.getBean().getCredit().getCreditLimit()){
            Notification.show("Credit limit exceeded").setPosition(Notification.Position.MIDDLE);
        } else {
            CreditOffer creditOffer = binder.getBean();
            creditOfferService.updateCreditOffer(creditOffer);
            creditOfferView.updateList();
            setCreditOffer(null);
        }
    }

    public void setCreditOffer(CreditOffer creditOffer) {
        binder.setBean(creditOffer);

        if (creditOffer == null) {
            setVisible(false);
        } else {
            setVisible(true);
            sumCredit.focus();
        }
    }
}
