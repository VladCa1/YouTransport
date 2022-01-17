package com.trans.views.myOffers;

import javax.annotation.PostConstruct;

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
@Route(value = "/myoffers", layout = MainLayout.class)
public class MyOffersViewImpl extends VerticalLayout implements MyOffersView, BeforeEnterObserver{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2195852060162910959L;
	
	private final MyOffersPresenter presenter;
	
	@Getter
	private PaginatedGrid<GoodsFormResultEntryDTO> goodsOfferGrid;
	
	@Getter
	private Button createNewOfferButton;
	
	@Autowired
	public MyOffersViewImpl(MyOffersPresenter presenter) {
		this.presenter = presenter;
		this.presenter.setView(this);
		initComponents();
		this.presenter.init();
		// TODO Auto-generated constructor stub
	}

	private void initComponents() {
		createNewOfferButton = new Button("New Offer");
		createNewOfferButton.getStyle().set("margin-left", "auto");
		goodsOfferGrid = new PaginatedGrid<>();
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
			fromLayout.add(new Span("From:"), new Span(offer.getToLocationCountry() + ", " + offer.getToLocationCity()));
			toLayout.add(new Span("To:"), new Span(offer.getFromLocationCountry() + ", " + offer.getFromLocationCity()));
			component.add(fromLayout, toLayout);
			return component;
		})).setHeader("Route");
		goodsOfferGrid.addColumn(new ComponentRenderer<>(offer ->{
			VerticalLayout component = new VerticalLayout();
			HorizontalLayout typeLayout = new HorizontalLayout();
			HorizontalLayout valueLayout = new HorizontalLayout();
			typeLayout.add(new Span("Type:"), new Span(offer.getPayType()));
			valueLayout.add(new Span("Value:"), new Span(offer.getPayValue().toString()));
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
			HorizontalLayout descLayout = new HorizontalLayout();
			HorizontalLayout char1Layout = new HorizontalLayout();
			descLayout.add(new Text(offer.getDescription()));
			char1Layout.add(new Text(offer.getCharacteristicOne() + "; " + offer.getCharacteristicTwo()));
			component.add(descLayout, char1Layout);
			return component;
		})).setHeader("Description");
		goodsOfferGrid.addColumn(new ComponentRenderer<>(offer ->{
			HorizontalLayout component = new HorizontalLayout();
			Button deleteButton = new Button("Delete");
			deleteButton.addClickListener(presenter.getDeleteOfferClickHandler(offer));
			component.add(deleteButton);
			return component;
		})).setHeader("Actions");
				
	}
	
	@PostConstruct
	public void postConstruct() {
		add(createNewOfferButton);
		if(SecurityUtils.getUserRoles().contains(SecurityUtils.ROLE_GOODS)) {
			add(goodsOfferGrid);
		}
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		
		
	}
	
}
