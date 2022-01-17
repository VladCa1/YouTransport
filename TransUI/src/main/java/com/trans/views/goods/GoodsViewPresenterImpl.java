package com.trans.views.goods;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.trans.serviceInterface.models.GoodsFormResultEntryDTO;
import com.trans.services.OfferService;
import com.trans.views.goods.GoodsView.GoodsViewPresenter;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import lombok.Getter;
import lombok.Setter;

@SpringComponent
@UIScope
public class GoodsViewPresenterImpl implements GoodsViewPresenter{

	@Getter
	@Setter
	private GoodsView view;
	
	@Autowired
	private OfferService offerService;
	
	@Getter
	private boolean isGridPopulated;

	@Override
	public void init() {
		populateGrid();
		
	}

	private void populateGrid() {
		List<GoodsFormResultEntryDTO> result = offerService.getAllGoodsOffers();
		view.getGrid().setDataProvider(new ListDataProvider<GoodsFormResultEntryDTO>(result));
		isGridPopulated = true;
		
	}


	@Override
	public void refreshGrid() {
		populateGrid();
		
	}
	
}
