package com.trans.views.goods;

import com.trans.views.myOffers.forms.GoodsOfferForm;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.textfield.TextField;

import java.net.URISyntaxException;

public interface GoodsOfferView {

    Long getParameter();

    GoodsOfferForm getForm();

    void setImage(Image profile);

    TextField getPhoneField();

    TextField getEmailField();

    TextField getCompanyField();

    TextField getNameField();

    public interface GoodsOfferViewPresenter {


        void init();

        void setView(GoodsOfferView goodsOfferView);

        void populateForm() throws Exception;

        void populatePersonLayout() throws Exception;

        void makeFormReadOnly(GoodsOfferForm form) throws URISyntaxException;

        Image getImage();
    }

}
