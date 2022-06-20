package com.trans.views.transport;

import com.trans.serviceInterface.models.TransportFormResultDTO;
import com.trans.views.MainLayout;
import com.trans.views.widgets.LocationSelect;
import com.trans.views.widgets.PayCurrencySelect;
import com.trans.views.widgets.PayTypeSelect;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.Getter;
import lombok.Setter;
import org.vaadin.klaudeta.PaginatedGrid;

import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@PageTitle("Transport You - Transport offers")
@Route(value = "/transport", layout = MainLayout.class)
public class TransportViewImpl extends VerticalLayout implements TransportView, BeforeEnterObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3362393261776234525L;

	@Getter
	private PaginatedGrid<TransportFormResultDTO> grid;

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
	private HorizontalLayout fromDateWrapper;

	@Getter
	private LocationSelect toLocationSelect;

	@Getter
	private LocationSelect fromLocationSelect;

	@Getter
	@Setter
	private CheckboxGroup<String> goodsTypeBoxes;

	private HorizontalLayout goodsTypeWrapperFilter;

	private HorizontalLayout buttonsWrapperFilter;

	private HorizontalLayout payWrapperFilter;

	@Getter
	private PayTypeSelect payTypeSelect;

	@Getter
	private PayCurrencySelect payCurrencySelect;

	@Getter
	private NumberField minPayValue;

	@Getter
	private DatePicker fromDate;

	@Getter
	private Button clearButton;

	@Getter
	private Button filterButton;


	private final TransportView.TransportPresenter presenter;

	public TransportViewImpl(TransportView.TransportPresenter presenter) throws URISyntaxException {
		this.presenter = presenter;
		this.presenter.setView(this);
		initElements();
		this.presenter.init();
		bind();
	}

	private void bind() {
		this.add(filterToggle);
		this.add(filterLayout);
		this.add(grid);
	}

	private void initElements() throws URISyntaxException {

		initFilter();

		grid = new PaginatedGrid<>();
		grid.addColumn(new ComponentRenderer<>(offer ->{
			VerticalLayout component = new VerticalLayout();
			switch(offer.getVehicleType()){
				case "SmallVehicle":
					component.add(new Icon(VaadinIcon.CAR));
					break;
				case "BigVehicle":
					component.add(new HorizontalLayout(new Icon(VaadinIcon.CAR), new Icon(VaadinIcon.CAR)));
					break;
				case "Truck":
					component.add(new Icon(VaadinIcon.TRUCK));
					break;
			}
			return component;
		})).setKey("icons");
		grid.getColumnByKey("icons").setAutoWidth(true);
		grid.addColumn(new ComponentRenderer<>(offer ->{
			VerticalLayout component = new VerticalLayout();
			HorizontalLayout typeLayout = new HorizontalLayout();
			HorizontalLayout makeLayout = new HorizontalLayout();
			HorizontalLayout makeType = new HorizontalLayout();
			HorizontalLayout tonnage = new HorizontalLayout();
			typeLayout.add(new Span("Vehicle Type:"), new Span(offer.getVehicleType()));
			makeLayout.add(new Span("Make:"), new Span(offer.getMake()));
			makeType.add(new Span("Make Type:"), new Span(offer.getType()));
			tonnage.add(new Span("Tonnage"), new Span(String.valueOf(offer.getTonnage())));
			component.add(typeLayout, makeLayout, makeType, tonnage);
			return component;
		})).setHeader("Vehicle").setKey("vehicle");
		grid.getColumnByKey("vehicle").setWidth("150px");
		grid.addColumn(new ComponentRenderer<>(offer ->{
			VerticalLayout component = new VerticalLayout();
			HorizontalLayout fromLayout = new HorizontalLayout();
			HorizontalLayout toLayout = new HorizontalLayout();
			fromLayout.add(new Span("From:"), new Span(offer.getFromLocationCountry() + ", " + offer.getFromLocationCity()));
			if(offer.getMaxAcceptedDistanceFromSource() == null){
				toLayout.add(new Span("To:"), new Span(offer.getToLocationCountry() + ", " + offer.getToLocationCity()));
			}else {
				toLayout.add(new Span("Max accepted distance from source: " + offer.getMaxAcceptedDistanceFromSource().toString() + "km"));
			}
			component.add(fromLayout, toLayout);
			component.setWidthFull();
			return component;
		})).setHeader("Route").setKey("route");
		grid.getColumnByKey("route").setAutoWidth(true);
		grid.addColumn(new ComponentRenderer<>(offer ->{
			VerticalLayout component = new VerticalLayout();
			HorizontalLayout typeLayout = new HorizontalLayout();
			HorizontalLayout valueLayout = new HorizontalLayout();
			typeLayout.add(new Span("Type:"), new Span(offer.getPayType()));
			valueLayout.add(new Span("Rate:"), new Span(offer.getPayValue().toString() + " " + offer.getPayCurrency()));
			component.add(typeLayout, valueLayout);
			return component;
		})).setHeader("Pay").setKey("pay");
		grid.getColumnByKey("pay").setWidth("170px");
		grid.addColumn(new ComponentRenderer<>(offer ->{
			VerticalLayout component = new VerticalLayout();
			HorizontalLayout typeLayout = new HorizontalLayout();
			HorizontalLayout valueLayout = new HorizontalLayout();
			typeLayout.add(new Span("Hazardous:"), new Span(offer.getHazardousMaterialsAccepted().toString()));
			valueLayout.add(new Span("Types:"), new Span(offer.getGoodsTypes().toString().replaceAll("\\[|\\]","").replaceAll(","," ")));
			component.add(typeLayout, valueLayout);
			return component;
		})).setHeader("Goods Types").setKey("types");
		grid.getColumnByKey("types").setWidth("200px");
		grid.addColumn(new ComponentRenderer<>(offer ->{
			VerticalLayout component = new VerticalLayout();
			HorizontalLayout fromLayout = new HorizontalLayout();
			fromLayout.add(new Span("From:"), new Span(offer.getFromDate().toString()));
			component.add(fromLayout);
			return component;
		})).setHeader("Date");
		grid.addColumn(new ComponentRenderer<>(offer ->{
			HorizontalLayout component = new HorizontalLayout();
			Button viewButton = new Button("View");
			viewButton.addClickListener(event ->{
				UI.getCurrent().navigate("/transport/offer/" + offer.getId());
			});
			component.add(viewButton);
			return component;
		})).setHeader("Actions").setKey("actions");
		grid.getColumnByKey("actions").setWidth("120px");
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		if(presenter.isGridPopulated()){
			presenter.refreshGrid();
		}
	}

	@Override
	public void resetFilters() {

		payTypeSelect.clear();
		payCurrencySelect.setValue("");
		minPayValue.setValue(null);
		toLocationSelect.getCountrySelect().setValue("");
		toLocationSelect.getCitySelect().setValue("");
		fromLocationSelect.getCountrySelect().setValue("");
		fromLocationSelect.getCitySelect().setValue("");
		goodsTypeBoxes.setValue(null);
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

		initGoodsTypeWrapper();
		initDistanceWrapper();
		initPayWrapper();
		initFromWrapper();
		initButtonsWrapper();

		buttonsWrapperFilter.getStyle().set("margin-top", "auto");
		filterV1Layout = new VerticalLayout(goodsTypeWrapperFilter, payWrapperFilter, fromDateWrapper, buttonsWrapperFilter);
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

		VerticalLayout locationsWrapper = new VerticalLayout(new Span("From:"), fromLocationSelect, new Span("To:"), toLocationSelect);

		presenter.addChangeBehaviourForLocationSelect(fromLocationSelect);
		presenter.addChangeBehaviourForLocationSelect(toLocationSelect);
		distanceWrapperFilter.add(locationsWrapper);
	}

	private void initGoodsTypeWrapper() {

		goodsTypeWrapperFilter = new HorizontalLayout();

		goodsTypeBoxes = new CheckboxGroup<>();

		List<String> types = new ArrayList<String>() {/**
		 *
		 */
		private static final long serialVersionUID = 3979746780930797698L;

			{
				add("Free flow");
				add("Pallet goods");
				add("Liquid goods");
				add("Hazardous");
			}
		};
		goodsTypeBoxes.setLabel("Goods type accepted:");
		goodsTypeBoxes.setDataProvider(new ListDataProvider<String>(types));
		goodsTypeWrapperFilter.add(goodsTypeBoxes);

	}

	private void initFromWrapper(){
		fromDateWrapper = new HorizontalLayout();
		fromDate = new DatePicker("From");
		fromDateWrapper.add(fromDate);
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
	public Set<String> getGoodsTypesValue() { return goodsTypeBoxes.getValue();}

	@Override
	public LocalDate getFromDate() { return fromDate.getValue();}

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
