package com.trans.views.myOffers;

import java.net.URISyntaxException;

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

		public boolean saveUpdate(GoodsFormResultEntryDTO goodsFormResult);

		public ComponentEventListener<ClickEvent<Button>> getDeleteOfferClickHandler(GoodsFormResultEntryDTO offer);
		
	}

	public Button getCreateNewOfferButton();
	
	public PaginatedGrid<GoodsFormResultEntryDTO> getGoodsOfferGrid();
}
