package com.trans.views.goods;

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
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import lombok.Getter;

@PageTitle("Transport You - Goods offers")
@Route(value = "/goods", layout = MainLayout.class)
public class GoodsViewImpl extends VerticalLayout implements GoodsView, BeforeEnterObserver{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7803597024426854821L;
	
	@Getter
	private PaginatedGrid<GoodsFormResultEntryDTO> grid;
	
	private final GoodsViewPresenter presenter;
	
	@Autowired
	public GoodsViewImpl(GoodsViewPresenter presenter) {
		this.presenter = presenter;
		this.presenter.setView(this);
		this.add(new H2("Goods Offer - Pick your desired one"));
		initComponents();
		this.presenter.init();
		bind();
	}

	private void bind() {
		this.add(grid);
		
	}

	private void initComponents() {
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
			fromLayout.add(new Span("From:"), new Span(offer.getToLocationCountry() + ", " + offer.getToLocationCity()));
			toLayout.add(new Span("To:"), new Span(offer.getFromLocationCountry() + ", " + offer.getFromLocationCity()));
			component.add(fromLayout, toLayout);
			return component;
		})).setHeader("Route");
		grid.addColumn(new ComponentRenderer<>(offer ->{
			VerticalLayout component = new VerticalLayout();
			HorizontalLayout typeLayout = new HorizontalLayout();
			HorizontalLayout valueLayout = new HorizontalLayout();
			typeLayout.add(new Span("Type:"), new Span(offer.getPayType()));
			valueLayout.add(new Span("Value:"), new Span(offer.getPayValue().toString()));
			component.add(typeLayout, valueLayout);			
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
			HorizontalLayout descLayout = new HorizontalLayout();
			HorizontalLayout char1Layout = new HorizontalLayout();
			descLayout.add(new Text(offer.getDescription()));
			char1Layout.add(new Text(offer.getCharacteristicOne() + "; " + offer.getCharacteristicTwo()));
			component.add(descLayout, char1Layout);
			return component;
		})).setHeader("Description");
		
		
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		if(presenter.isGridPopulated()) {
			presenter.refreshGrid();
		}
		
	}
}