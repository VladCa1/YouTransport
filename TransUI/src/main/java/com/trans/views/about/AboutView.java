package com.trans.views.about;

import com.trans.views.MainLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@PageTitle("About")
@Route(value = "about", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class AboutView extends VerticalLayout {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5062006030205086041L;

	public AboutView() {
        setSpacing(false);
        
        add(new H2("Transport You - Now, Anywhere, Anytime"));
        Image img = new Image("images/about_image.jpg", "about tir");
        add(img);

       
        add(new Paragraph("Please Login to access our service"));
        add(new Paragraph("Or Register if you are new ;)"));

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

}
