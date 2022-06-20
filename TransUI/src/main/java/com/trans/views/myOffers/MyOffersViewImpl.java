package com.trans.views.myOffers;

import javax.annotation.PostConstruct;

import com.trans.serviceInterface.models.TransportFormResultDTO;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.klaudeta.PaginatedGrid;

import com.trans.serviceInterface.models.GoodsFormResultEntryDTO;
import com.trans.utils.SecurityUtils;
import com.trans.views.MainLayout;
import com.trans.views.widgets.GoodsTypeSelect;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import lombok.Getter;

@PageTitle("Transport You - My Offers")
@Route(value = "/my-offers", layout = MainLayout.class)
public class MyOffersViewImpl extends VerticalLayout implements MyOffersView, BeforeEnterObserver{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2195852060162910959L;
	
	private final MyOffersPresenter presenter;
	
	@Getter
	private PaginatedGrid<GoodsFormResultEntryDTO> goodsOfferGrid;

	@Getter
	private PaginatedGrid<TransportFormResultDTO> transportOfferGrid;
	
	@Getter
	private Button createNewOfferButton;
	
	@Autowired
	public MyOffersViewImpl(MyOffersPresenter presenter) {
		this.presenter = presenter;
		this.presenter.setView(this);
		initComponents();
		this.presenter.init();

	}

	private void initComponents() {
		createNewOfferButton = new Button("New Offer");
		createNewOfferButton.getStyle().set("margin-left", "auto");
		createNewOfferButton.setIcon(new Icon(VaadinIcon.PLUS));
		goodsOfferGrid = new PaginatedGrid<>();
		initGoodsGrid();
		transportOfferGrid = new PaginatedGrid<>();
		initTransportGrid();
				
	}

	private void initTransportGrid() {
		transportOfferGrid.addColumn(new ComponentRenderer<>(offer ->{
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
		transportOfferGrid.getColumnByKey("vehicle").setWidth("150px");
		transportOfferGrid.addColumn(new ComponentRenderer<>(offer ->{
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
		transportOfferGrid.getColumnByKey("route").setWidth("250px");
		transportOfferGrid.addColumn(new ComponentRenderer<>(offer ->{
			VerticalLayout component = new VerticalLayout();
			HorizontalLayout typeLayout = new HorizontalLayout();
			HorizontalLayout valueLayout = new HorizontalLayout();
			typeLayout.add(new Span("Type:"), new Span(offer.getPayType()));
			valueLayout.add(new Span("Rate:"), new Span(offer.getPayValue().toString() + " " + offer.getPayCurrency()));
			component.add(typeLayout, valueLayout);
			return component;
		})).setHeader("Pay");
		transportOfferGrid.addColumn(new ComponentRenderer<>(offer ->{
			VerticalLayout component = new VerticalLayout();
			HorizontalLayout typeLayout = new HorizontalLayout();
			HorizontalLayout valueLayout = new HorizontalLayout();
			typeLayout.add(new Span("Hazardous:"), new Span(offer.getHazardousMaterialsAccepted().toString()));
			valueLayout.add(new Span("Types:"), new Span(offer.getGoodsTypes().toString().replaceAll("\\[|\\]","").replaceAll(","," ")));
			component.add(typeLayout, valueLayout);
			return component;
		})).setHeader("Goods Types").setKey("types");
		transportOfferGrid.getColumnByKey("types").setWidth("200px");
		transportOfferGrid.addColumn(new ComponentRenderer<>(offer ->{
			VerticalLayout component = new VerticalLayout();
			HorizontalLayout typeLayout = new HorizontalLayout();
			typeLayout.add(new Span("Driver:"), new Span(offer.getDriver().getFirstName() + " " + offer.getDriver().getLastName()));
			component.add(typeLayout);
			return component;
		})).setHeader("Driver");
		transportOfferGrid.addColumn(new ComponentRenderer<>(offer ->{
			VerticalLayout component = new VerticalLayout();
			HorizontalLayout fromLayout = new HorizontalLayout();
			fromLayout.add(new Span("From:"), new Span(offer.getFromDate().toString()));
			component.add(fromLayout);
			return component;
		})).setHeader("Date");
		transportOfferGrid.addColumn(new ComponentRenderer<>(offer ->{
			HorizontalLayout component = new HorizontalLayout();
			Button deleteButton = new Button("Delete");
			Button viewButton = new Button("View");
			deleteButton.addClickListener(presenter.getDeleteTransportOfferClickHandler(offer));
			viewButton.addClickListener(presenter.getViewTransportOfferClickHandler(offer));
			component.add(deleteButton);
			component.add(viewButton);
			return component;
		})).setHeader("Actions").setKey("actions");
		transportOfferGrid.getColumnByKey("actions").setWidth("120px");

	}

	private void initGoodsGrid() {
		goodsOfferGrid.addColumn(new ComponentRenderer<>(offer ->{
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
		goodsOfferGrid.addColumn(new ComponentRenderer<>(offer ->{
			VerticalLayout component = new VerticalLayout();
			HorizontalLayout fromLayout = new HorizontalLayout();
			HorizontalLayout toLayout = new HorizontalLayout();
			fromLayout.add(new Span("From:"), new Span(offer.getFromLocationCountry() + ", " + offer.getFromLocationCity()));
			toLayout.add(new Span("To:"), new Span(offer.getToLocationCountry() + ", " + offer.getToLocationCity()));
			component.add(fromLayout, toLayout);
			component.setWidthFull();
			return component;
		})).setHeader("Route");
		goodsOfferGrid.addColumn(new ComponentRenderer<>(offer ->{
			VerticalLayout component = new VerticalLayout();
			HorizontalLayout typeLayout = new HorizontalLayout();
			HorizontalLayout valueLayout = new HorizontalLayout();
			typeLayout.add(new Span("Type:"), new Span(offer.getPayType()));
			valueLayout.add(new Span("Rate:"), new Span(offer.getPayValue().toString() + " " + offer.getPayCurrency()));
			component.add(typeLayout, valueLayout);
			return component;
		})).setHeader("Pay");
		goodsOfferGrid.addColumn(new ComponentRenderer<>(offer ->{
			VerticalLayout component = new VerticalLayout();
			HorizontalLayout fromLayout = new HorizontalLayout();
			HorizontalLayout toLayout = new HorizontalLayout();
			fromLayout.add(new Span("From:"), new Span(offer.getFromDate().toString()));
			toLayout.add(new Span("To:"), new Span(offer.getToDate().toString()));
			component.add(fromLayout, toLayout);
			return component;
		})).setHeader("Date");
		goodsOfferGrid.addColumn(new ComponentRenderer<>(offer ->{
			VerticalLayout component = new VerticalLayout();
			HorizontalLayout charLayout = new HorizontalLayout();
			String charText = offer.getCharacteristicOne();
			if(offer.getCharacteristicTwo() != null){
				charText += " | " + offer.getCharacteristicTwo();
			}
			charLayout.add(new Text(charText));
			component.add(charLayout);
			return component;
		})).setHeader("Description");
		goodsOfferGrid.addColumn(new ComponentRenderer<>(offer ->{
			HorizontalLayout component = new HorizontalLayout();
			Button deleteButton = new Button("Delete");
			Button viewButton = new Button("View");
			deleteButton.addClickListener(presenter.getDeleteGoodsOfferClickHandler(offer));
			viewButton.addClickListener(presenter.getViewGoodsOfferClickHandler(offer));
			component.add(deleteButton);
			component.add(viewButton);
			return component;
		})).setHeader("Actions");
	}

	@PostConstruct
	public void postConstruct() {
		add(createNewOfferButton);
		if(SecurityUtils.getUserRoles().contains(SecurityUtils.ROLE_GOODS)) {
			add(goodsOfferGrid);
		}else if(SecurityUtils.getUserRoles().contains(SecurityUtils.ROLE_TRANSPORT)){
			add(transportOfferGrid);
		}
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		
		
	}
	
}
