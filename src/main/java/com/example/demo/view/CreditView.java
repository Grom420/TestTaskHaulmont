package com.example.demo.view;

import com.example.demo.VaadinUI;
import com.example.demo.entitys.Credit;
import com.example.demo.frontend.CreditEdit;
import com.example.demo.services.CreditServiceImpl;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "/credits", layout = VaadinUI.class)
public class CreditView extends VerticalLayout {

    private final CreditServiceImpl creditServiceImpl;
    private CreditAddFormView creditAddFormView;
    private Grid<Credit> grid = new Grid<>(Credit.class);
    private CreditEdit creditEdit;

    public CreditView(CreditServiceImpl creditServiceImpl) {
        creditAddFormView = new CreditAddFormView(this, creditServiceImpl);
        creditEdit = new CreditEdit(this, creditServiceImpl);
        this.creditServiceImpl = creditServiceImpl;
        add(creditAddFormView);
        creditEdit.setCredit(null);
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        HorizontalLayout mainContent = new HorizontalLayout(grid, creditEdit);
        mainContent.setSizeFull();
        add(mainContent);
        updateList();
        grid.asSingleSelect().addValueChangeListener(event ->
                creditEdit.setCredit(grid.asSingleSelect().getValue()));
    }

    private void configureGrid() {
        grid.addClassName("credit-grid");
        grid.setSizeFull();
        grid.setColumns("creditUUID", "creditName", "creditLimit", "interestRate");
    }

    public void updateList() {
        grid.setItems(creditServiceImpl.findAll());
    }
}
