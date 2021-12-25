package com.trans.views.goods;

import com.trans.views.MainLayout;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Transport You - Goods offers")
@Route(value = "/goods", layout = MainLayout.class)
public class GoodsView extends VerticalLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7803597024426854821L;
	
	public GoodsView() {
		this.add(new Text("Goods User"));
	}
}
