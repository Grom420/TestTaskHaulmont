package com.example.demo;

import com.example.demo.view.ClientView;
import com.example.demo.view.CreditOfferView;
import com.example.demo.view.CreditView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;

@Route("/")
public class VaadinUI extends VerticalLayout implements RouterLayout {


    public VaadinUI() {
        Tab tabClient = createMenuItem("Clients", ClientView.class);
        Tab tabCredits = createMenuItem("Credits", CreditView.class);
        Tab tabOffer = createMenuItem("Offers", CreditOfferView.class);
        Tabs tabs = new Tabs(false, tabClient, tabCredits, tabOffer);
        setSizeFull();
        add(tabs);
    }

    private Tab createMenuItem(String title, Class<? extends Component> target) {
        RouterLink link = new RouterLink(null,target);
        link.add(title);
        Tab tab = new Tab();
        tab.add(link);
        return tab;
    }
}