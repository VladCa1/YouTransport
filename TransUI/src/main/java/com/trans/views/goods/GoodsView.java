package com.trans.views.goods;

import com.trans.views.widgets.LocationSelect;
import com.trans.views.widgets.PayTypeSelect;
import com.vaadin.flow.component.textfield.NumberField;
import org.vaadin.klaudeta.PaginatedGrid;

import com.trans.serviceInterface.models.GoodsFormResultEntryDTO;

import java.net.URISyntaxException;

public interface GoodsView {

	public interface GoodsViewPresenter {

		void setView(GoodsView goodsView);

		void init();

		boolean isGridPopulated();

        void addChangeBehaviourForLocationSelect(LocationSelect select) throws URISyntaxException;

		void addChangeBehaviourForLocationSelectWithDistanceField(LocationSelect select, NumberField maxDistance) throws URISyntaxException;

		void refreshGrid();

        void applyFilters();

    }

	public PaginatedGrid<GoodsFormResultEntryDTO> getGrid();

	String getPaySelectFilterValue();

	String getPayCurrencyValue();

	Double getPayMinValue();

	String getGoodsTypeValue();

	Double getGoodsTypeSizeValue();

	Double getGoodsPalletSizeValue();

	Double getMaxDistanceValue();

	String getFromCountryValue();

	String getFromCityValue();

	String getToCountryValue();

	String getToCityValue();
}
