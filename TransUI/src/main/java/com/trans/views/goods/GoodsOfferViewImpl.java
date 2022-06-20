package com.trans.views.goods;

import com.trans.views.MainLayout;
import com.trans.views.myOffers.forms.GoodsOfferForm;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URISyntaxException;

@PageTitle("Transport You - Goods offer")
@Route(value = "/goods/offer", layout = MainLayout.class)
public class GoodsOfferViewImpl extends VerticalLayout implements GoodsOfferView, HasUrlParameter<String> {

    private final GoodsOfferViewPresenter presenter;

    @Getter
    private GoodsOfferForm form;

    private HorizontalLayout mainLayout;

    private VerticalLayout offerOwnerLayout;

    @Getter
    private TextField nameField;

    @Getter
    private TextField phoneField;

    @Getter
    private TextField companyField;

    @Getter
    private TextField emailField;

    @Getter
    @Setter
    private Image image;

    private H1 title;

    @Getter
    private Long parameter;

    @Autowired
    public GoodsOfferViewImpl(GoodsOfferViewPresenter presenter){
        this.presenter = presenter;
        this.presenter.setView(this);
        initElements();
        this.presenter.init();

    }

    private void initElements() {
        mainLayout = new HorizontalLayout();
        offerOwnerLayout = new VerticalLayout();
        form = new GoodsOfferForm();

        nameField = new TextField("Name:");
        nameField.setReadOnly(true);
        phoneField = new TextField("Phone:");
        phoneField.setReadOnly(true);
        companyField = new TextField("Company Details:");
        companyField.setReadOnly(true);
        emailField = new TextField("Email:");
        emailField.setReadOnly(true);

    }

    @Override
    public void setParameter(BeforeEvent event, String offerID) {
        parameter = Long.valueOf(offerID);
        try {
            this.presenter.makeFormReadOnly(form);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        try {
            this.presenter.populateForm();
            this.presenter.populatePersonLayout();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.mainLayout.add(form);
        this.offerOwnerLayout.add(image, nameField, emailField, phoneField, companyField);
        this.mainLayout.add(offerOwnerLayout);
        this.offerOwnerLayout.getStyle().set("border", "1px solid lightgray");
        this.offerOwnerLayout.getStyle().set("margin-left", "10px");
        this.add(mainLayout);
    }

}
