package com.example.demo.view;

import com.example.demo.VaadinUI;
import com.example.demo.entitys.Client;
import com.example.demo.frontend.ClientEdit;
import com.example.demo.services.ClientServiceImpl;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "/clients", layout = VaadinUI.class)
public class ClientView extends VerticalLayout {

    private ClientServiceImpl clientServiceImpl;
    private ClientAddFormView clientAddFormView;
    private Grid<Client> grid = new Grid<>(Client.class);
    private ClientEdit clientEdit;

    public ClientView(ClientServiceImpl clientServiceImpl) {
        this.clientServiceImpl = clientServiceImpl;
        clientEdit = new ClientEdit(this, clientServiceImpl);
        clientAddFormView = new ClientAddFormView(this, clientServiceImpl);
        add(clientAddFormView);

        clientEdit.setClient(null);
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        HorizontalLayout mainContent = new HorizontalLayout(grid, clientEdit);
        mainContent.setSizeFull();
        add(mainContent);
        updateList();
        grid.asSingleSelect().addValueChangeListener(event ->
                clientEdit.setClient(grid.asSingleSelect().getValue()));
    }

    private void configureGrid() {
        grid.addClassName("contact-grid");
        grid.setSizeFull();
        grid.setColumns("clientUUID", "name", "surname", "lastname" ,"email", "number", "passport");
    }

    public void updateList() {
        grid.setItems(clientServiceImpl.findAll());
    }
}
