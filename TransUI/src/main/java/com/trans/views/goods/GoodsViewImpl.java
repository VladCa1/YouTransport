package com.trans.views.goods;

import com.trans.views.widgets.LocationSelect;
import com.trans.views.widgets.PayCurrencySelect;
import com.trans.views.widgets.PayTypeSelect;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.*;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.klaudeta.PaginatedGrid;

import com.trans.serviceInterface.models.GoodsFormResultEntryDTO;
import com.trans.views.MainLayout;
import com.trans.views.widgets.GoodsTypeSelect;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;

import lombok.Getter;

import java.net.URISyntaxException;

@PageTitle("Transport You - Goods offers")
@Route(value = "/goods", layout = MainLayout.class)
public class GoodsViewImpl extends VerticalLayout implements GoodsView, BeforeEnterObserver{

	/**s
	 * 
	 */
	private static final long serialVersionUID = 7803597024426854821L;
	
	@Getter
	private PaginatedGrid<GoodsFormResultEntryDTO> grid;

	@Getter
	private Button filterToggle;

	@Getter
	private HorizontalLayout filterLayout;

	@Getter
	private VerticalLayout filterV1Layout;

	@Getter
	private VerticalLayout filterV2Layout;

	@Getter
	private HorizontalLayout distanceWrapperFilter;

	@Getter
	private LocationSelect toLocationSelect;

	@Getter
	private LocationSelect fromLocationSelect;

	@Getter
	private NumberField maxDistanceAvailable;

	@Getter
	private HorizontalLayout goodsWrapperFilter;

	@Getter
	private GoodsTypeSelect goodsTypeSelect;

	@Getter
	@Setter
	private NumberField goodsSizeNumberField;

	@Getter
	@Setter
	private NumberField goodsPalletSizeNumberField;

	@Getter
	private HorizontalLayout payWrapperFilter;

	@Getter
	private PayTypeSelect payTypeSelect;

	@Getter
	private PayCurrencySelect payCurrencySelect;

	@Getter
	private NumberField minPayValue;

	@Getter
	private HorizontalLayout buttonsWrapperFilter;

	@Getter
	private Button clearButton;

	@Getter
	private Button filterButton;

	private final GoodsViewPresenter presenter;
	
	@Autowired
	public GoodsViewImpl(GoodsViewPresenter presenter) {
		this.presenter = presenter;
		this.presenter.setView(this);
		this.add(new H2("Goods Offer - Pick your desired one"));
		try {
			initComponents();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		this.presenter.init();
		bind();
	}

	private void bind() {
		this.add(filterToggle);
		this.add(filterLayout);
		this.add(grid);
		
	}

	private void initComponents() throws URISyntaxException {

		initFilter();

		grid = new PaginatedGrid<>();
		grid.addColumn(new ComponentRenderer<>(offer ->{
			HorizontalLayout component = new HorizontalLayout();
			switch(offer.getGoodsType()) {
			case GoodsTypeSelect.FREE_FLOW_GOODS:
				component.add(new Image("images/freeflow1_icon.png", "Grains"));
				component.add(new Image("images/rock_icon.jfif", "Rocks"));
				break;
			case GoodsTypeSelect.LIQUID_GOODS:
				component.add(new Image("images/liquid_icon.jpg", "Liquid"));
				break;
			case GoodsTypeSelect.PALLET_GOODS:
				component.add(new Image("images/pallet_icon.png", "Liquid"));
				break;
			default:
				break;
			}
			component.setHeight("35px");
			component.setWidth("35px");
			return component;
		}));
		grid.addColumn(new ComponentRenderer<>(offer ->{
			VerticalLayout component = new VerticalLayout();
			HorizontalLayout typeLayout = new HorizontalLayout();
			HorizontalLayout valueLayout = new HorizontalLayout();
			typeLayout.add(new Span("Type:"), new Span(offer.getGoodsType()));
			valueLayout.add(new Span("Size:"), new Span(offer.getGoodsSize().toString()));
			component.add(typeLayout, valueLayout);
			if(offer.getGoodsType().equals(GoodsTypeSelect.PALLET_GOODS)) {
				HorizontalLayout palletSizeLayout = new HorizontalLayout();
				palletSizeLayout.add(new Span("Pallet Size:"), new Span(offer.getGoodsPalletSize().toString()));
				component.add(palletSizeLayout);
			}					
			return component;
		})).setHeader("Goods");
		grid.addColumn(new ComponentRenderer<>(offer ->{
			VerticalLayout component = new VerticalLayout();
			HorizontalLayout fromLayout = new HorizontalLayout();
			HorizontalLayout toLayout = new HorizontalLayout();
			fromLayout.add(new Span("From:"), new Span(offer.getFromLocationCountry() + ", " + offer.getFromLocationCity()));
			toLayout.add(new Span("To:"), new Span(offer.getToLocationCountry() + ", " + offer.getToLocationCity()));
			component.add(fromLayout, toLayout);
			component.setWidthFull();
			return component;
		})).setHeader("Route");
		grid.addColumn(new ComponentRenderer<>(offer ->{
			VerticalLayout component = new VerticalLayout();
			HorizontalLayout typeLayout = new HorizontalLayout();
			HorizontalLayout rateLayout = new HorizontalLayout();
			typeLayout.add(new Span("Type:"), new Span(offer.getPayType()));
			rateLayout.add(new Span("Rate:"), new Span(offer.getPayValue().toString() + " " + offer.getPayCurrency()));
			component.add(typeLayout, rateLayout);
			return component;
		})).setHeader("Pay");
		grid.addColumn(new ComponentRenderer<>(offer ->{
			VerticalLayout component = new VerticalLayout();
			HorizontalLayout fromLayout = new HorizontalLayout();
			HorizontalLayout toLayout = new HorizontalLayout();
			fromLayout.add(new Span("From:"), new Span(offer.getFromDate().toString()));
			toLayout.add(new Span("To:"), new Span(offer.getToDate().toString()));
			component.add(fromLayout, toLayout);
			return component;
		})).setHeader("Date");
		grid.addColumn(new ComponentRenderer<>(offer ->{
			VerticalLayout component = new VerticalLayout();
			HorizontalLayout char1Layout = new HorizontalLayout();
			String charText = offer.getCharacteristicOne();
			if(offer.getCharacteristicTwo() != null){
				charText += " | " + offer.getCharacteristicTwo();
			}
			char1Layout.add(new Text(charText));
			component.add(char1Layout);
			return component;
		})).setHeader("Description");
		grid.addColumn(new ComponentRenderer<>(offer ->{
			VerticalLayout component = new VerticalLayout();
			HorizontalLayout descLayout = new HorizontalLayout();
			String charText = offer.getDistance();
			descLayout.add(new Text(charText));
			component.add(descLayout);
			return component;
		})).setHeader("Distance");
		grid.addColumn(new ComponentRenderer<>(offer ->{
			VerticalLayout component = new VerticalLayout();
			Button viewButton = new Button("View");
			viewButton.addClickListener(event ->{
				UI.getCurrent().navigate("/goods/offer/" + offer.getId());
			});
			component.add(viewButton);
			return component;
		})).setHeader("Actions");
		
	}

	private void initFilter() throws URISyntaxException {

		filterToggle = new Button(new Icon(VaadinIcon.ARROW_DOWN));
		filterToggle.getElement().setAttribute("aria-label", "Filter toggle");
		filterToggle.addClickListener( event -> {
			if(!filterLayout.isVisible()) {
				filterLayout.setVisible(true);
			}else {
				filterLayout.setVisible(false);
			}

		});
		filterLayout = new HorizontalLayout();
		filterLayout.setVisible(false);
		filterLayout.getStyle().set("border", "1px solid lightgray");
		filterLayout.setWidthFull();

		initGoodsWrapper();
		initDistanceWrapper();
		initPayWrapper();
		initButtonsWrapper();

		buttonsWrapperFilter.getStyle().set("margin-top", "auto");
		filterV1Layout = new VerticalLayout(goodsWrapperFilter, payWrapperFilter, buttonsWrapperFilter);
		filterV2Layout = new VerticalLayout(distanceWrapperFilter);
		filterLayout.add(filterV1Layout, filterV2Layout);

	}

	private void initButtonsWrapper() {
		buttonsWrapperFilter = new HorizontalLayout();
		clearButton = new Button(new Icon(VaadinIcon.TRASH));
		clearButton.addClickListener(event ->{
			resetFilters();
		});
		filterButton = new Button(new Icon(VaadinIcon.FILTER));
		filterButton.addClickListener(event ->{
			presenter.applyFilters();
		});
		buttonsWrapperFilter.add(filterButton, clearButton);

	}

	private void resetFilters() {

		payTypeSelect.setValue("");
		payCurrencySelect.setValue("");
		minPayValue.setValue(null);
		toLocationSelect.getCountrySelect().setValue("");
		toLocationSelect.getCitySelect().setValue("");
		fromLocationSelect.getCountrySelect().setValue("");
		fromLocationSelect.getCitySelect().setValue("");
		maxDistanceAvailable.setValue(null);
		goodsTypeSelect.setValue("");
		goodsSizeNumberField.setValue(null);
		goodsPalletSizeNumberField.setValue(null);
	}

	private void initPayWrapper() {
		payWrapperFilter = new HorizontalLayout();
		payTypeSelect = new PayTypeSelect();
		payCurrencySelect = new PayCurrencySelect();
		minPayValue = new NumberField("Min Value:");

		payWrapperFilter.add(payTypeSelect);
		payWrapperFilter.add(minPayValue);
		payWrapperFilter.add(payCurrencySelect);

	}

	private void initDistanceWrapper() throws URISyntaxException {

		distanceWrapperFilter = new HorizontalLayout();

		toLocationSelect = new LocationSelect();
		fromLocationSelect = new LocationSelect();
		maxDistanceAvailable = new NumberField("Max Distance(km):");
		maxDistanceAvailable.addValueChangeListener( event ->{
			if(maxDistanceAvailable.getValue() != null){
				toLocationSelect.getCountrySelect().setEnabled(false);
				toLocationSelect.getCitySelect().setEnabled(false);
			}else {
				toLocationSelect.getCountrySelect().setEnabled(true);
			}
		});

		VerticalLayout locationsWrapper = new VerticalLayout(new Span("From:"), fromLocationSelect, new Span("To:"), toLocationSelect, maxDistanceAvailable);

		presenter.addChangeBehaviourForLocationSelect(fromLocationSelect);
		presenter.addChangeBehaviourForLocationSelectWithDistanceField(toLocationSelect, maxDistanceAvailable);
		distanceWrapperFilter.add(locationsWrapper);

	}

	private void initGoodsWrapper() {

		goodsWrapperFilter = new HorizontalLayout();

		goodsTypeSelect = new GoodsTypeSelect();
		goodsSizeNumberField = new NumberField("Max Size:");
		goodsPalletSizeNumberField = new NumberField("Max Pallet Size:");
		goodsPalletSizeNumberField.setVisible(false);

		goodsWrapperFilter.add(goodsTypeSelect);
		goodsWrapperFilter.add(goodsSizeNumberField);
		goodsWrapperFilter.add(goodsPalletSizeNumberField);
		goodsTypeSelect.addValueChangeListener(event ->{
			if(goodsTypeSelect.getValue() != null && goodsTypeSelect.getValue().equalsIgnoreCase(GoodsTypeSelect.PALLET_GOODS)) {
				goodsPalletSizeNumberField.setVisible(true);
			}else {
				goodsPalletSizeNumberField.setVisible(false);
			}
		});

	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		if(presenter.isGridPopulated()) {
			presenter.refreshGrid();
		}
		
	}

	@Override
	public String getPaySelectFilterValue() {
		return payTypeSelect.getValue();
	}

	@Override
	public String getPayCurrencyValue() {
		return payCurrencySelect.getValue();
	}

	@Override
	public Double getPayMinValue() {
		return minPayValue.getValue();
	}

	@Override
	public String getGoodsTypeValue() {
		return goodsTypeSelect.getValue();
	}

	@Override
	public Double getGoodsTypeSizeValue() {
		return goodsSizeNumberField.getValue();
	}

	@Override
	public Double getGoodsPalletSizeValue() {
		return goodsPalletSizeNumberField.getValue();
	}

	@Override
	public Double getMaxDistanceValue() {
		return maxDistanceAvailable.getValue();
	}

	@Override
	public String getFromCountryValue() {
		return fromLocationSelect.getCountrySelect().getValue();
	}

	@Override
	public String getFromCityValue() {
		return fromLocationSelect.getCitySelect().getValue();
	}

	@Override
	public String getToCountryValue() {
		return toLocationSelect.getCountrySelect().getValue();
	}

	@Override
	public String getToCityValue() {
		return toLocationSelect.getCitySelect().getValue();
	}
}