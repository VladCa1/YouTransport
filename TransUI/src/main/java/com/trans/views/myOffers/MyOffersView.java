package com.trans.views.myOffers;

import java.net.URISyntaxException;

import com.trans.serviceInterface.models.TransportFormResultDTO;
import com.trans.views.myOffers.forms.OfferForm;
import com.vaadin.flow.component.textfield.IntegerField;
import org.vaadin.klaudeta.PaginatedGrid;

import com.trans.serviceInterface.models.GoodsFormResultEntryDTO;
import com.trans.views.widgets.LocationSelect;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;

public interface MyOffersView {

	public interface MyOffersPresenter {
	
		public void setView(MyOffersView view);

		public void init();

		public void addChangeBehaviourForLocationSelect(LocationSelect fromLocationSelect) throws URISyntaxException;

		void addChangeBehaviourForLocationSelectWithDistanceField(LocationSelect select, IntegerField maxDistance) throws URISyntaxException;

		public boolean saveUpdate(GoodsFormResultEntryDTO goodsFormResult);

		boolean saveUpdate(TransportFormResultDTO transportFormResultDTO);

		public ComponentEventListener<ClickEvent<Button>> getDeleteGoodsOfferClickHandler(GoodsFormResultEntryDTO offer);

		public ComponentEventListener<ClickEvent<Button>> getDeleteTransportOfferClickHandler(TransportFormResultDTO offer);

		public ComponentEventListener<ClickEvent<Button>> getViewGoodsOfferClickHandler(GoodsFormResultEntryDTO offer);

		ComponentEventListener<ClickEvent<Button>> getViewTransportOfferClickHandler(TransportFormResultDTO offer);

		public void addUpdateSpecificGoodsButtons(OfferForm form, MyOfferViewImpl myOfferView);

		public void addUpdateSpecificTransportButtons(OfferForm form, MyOfferViewImpl myOfferView);
	}

	public Button getCreateNewOfferButton();
	
	public PaginatedGrid<GoodsFormResultEntryDTO> getGoodsOfferGrid();

	public PaginatedGrid<TransportFormResultDTO> getTransportOfferGrid();
}
