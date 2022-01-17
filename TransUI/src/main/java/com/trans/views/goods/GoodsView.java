package com.trans.views.goods;

import org.vaadin.klaudeta.PaginatedGrid;

import com.trans.serviceInterface.models.GoodsFormResultEntryDTO;

public interface GoodsView {

	public interface GoodsViewPresenter {

		void setView(GoodsView goodsView);

		void init();

		boolean isGridPopulated();

		void refreshGrid();
		
	}

	public PaginatedGrid<GoodsFormResultEntryDTO> getGrid();
}
