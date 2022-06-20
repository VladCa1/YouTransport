package com.trans.views;

import java.util.ArrayList;
import java.util.List;

import com.trans.views.drivers.DriverViewImpl;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import org.springframework.beans.factory.annotation.Autowired;

import com.trans.security.SecurityService;
import com.trans.utils.SecurityUtils;
import com.trans.views.about.AboutView;
import com.trans.views.goods.GoodsViewImpl;
import com.trans.views.login.LoginView;
import com.trans.views.login.RegisterView;
import com.trans.views.myOffers.MyOffersViewImpl;
import com.trans.views.transport.TransportViewImpl;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Nav;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;

/**
 * The main view is a top-level placeholder for other views.
 */
@PWA(name = "TransUI", shortName = "TransUI", enableInstallPrompt = false)
@Theme(themeFolder = "transui")
@PageTitle("Main")
public class MainLayout extends AppLayout {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6624743570562967130L;
	
	@Autowired
	private SecurityService securityService;

    public static class MenuItemInfo {

        private String text;
        private Icon iconClass;
        private Class<? extends Component> view;

        public MenuItemInfo(String text, Icon iconClass, Class<? extends Component> view) {
            this.text = text;
            this.iconClass = iconClass;
            this.view = view;
        }

        public String getText() {
            return text;
        }

        public Icon getIconClass() {
            return iconClass;
        }

        public Class<? extends Component> getView() {
            return view;
        }

    }

    private H1 viewTitle;

    public MainLayout() {
        setPrimarySection(Section.DRAWER);
        addToNavbar(true, createHeaderContent());
        addToDrawer(createDrawerContent());
    }

    private Component createHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.addClassName("text-secondary");
        toggle.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        toggle.getElement().setAttribute("aria-label", "Menu toggle");

        viewTitle = new H1();
        viewTitle.addClassNames("m-0", "text-l");

        Header header = new Header(toggle, viewTitle);
        header.addClassNames("bg-base", "border-b", "border-contrast-10", "box-border", "flex", "h-xl", "items-center",
                "w-full");
        return header;
    }

    private Component createDrawerContent() {
        H2 appName = new H2("TransUI");
        appName.addClassNames("flex", "items-center", "h-xl", "m-0", "px-m", "text-m");

        com.vaadin.flow.component.html.Section section = new com.vaadin.flow.component.html.Section(appName,
                createNavigation(), createFooter());
        section.addClassNames("flex", "flex-col", "items-stretch", "max-h-full", "min-h-full");
        return section;
    }
    
    private Nav createNavigation() {
        Nav nav = new Nav();
        nav.addClassNames("border-b", "border-contrast-10", "flex-grow", "overflow-auto");
        nav.getElement().setAttribute("aria-labelledby", "views");

        // Wrap the links in a list; improves accessibility
        UnorderedList list = new UnorderedList();
        list.addClassNames("list-none", "m-0", "p-0");
        nav.add(list);
        Button logout = new Button("Log out", e -> securityService.logout()); 
        Button login = new Button("Login", e -> UI.getCurrent().navigate(LoginView.class));
        Button register = new Button("Register", e -> UI.getCurrent().navigate(RegisterView.class));
        logout.getStyle().set("bottom", "0");
        logout.getStyle().set("position", "absolute");
        logout.getStyle().set("margin-left","50%");
        logout.getStyle().set("margin-bottom", "50px");
        logout.getStyle().set("transform","translateX(-50%)");
        login.getStyle().set("bottom", "0");
        login.getStyle().set("position", "absolute");
        login.getStyle().set("margin-left","50%");
        login.getStyle().set("margin-bottom", "100px");
        login.getStyle().set("transform","translateX(-50%)");
        register.getStyle().set("bottom", "0");
        register.getStyle().set("position", "absolute");
        register.getStyle().set("margin-left","50%");
        register.getStyle().set("margin-bottom", "50px");
        register.getStyle().set("transform","translateX(-50%)");
        
        if(SecurityUtils.isUserLoggedIn()) {
        	nav.add(logout);
        }else {
        	nav.add(login);
        	nav.add(register);
        }
        
        
        for (RouterLink link : createLinks()) {
            ListItem item = new ListItem(link);
            list.add(item);
        }
        return nav;
    }

    private List<RouterLink> createLinks() {
    	
    	
        List<MenuItemInfo> menuItems = new ArrayList<MenuItemInfo>() {/**
			 * 
			 */
			private static final long serialVersionUID = 6712929646047049448L;

		{	if(SecurityUtils.isUserLoggedIn()){
				add(new MenuItemInfo("My Offers", new Icon(VaadinIcon.NEWSPAPER), MyOffersViewImpl.class));
                if(SecurityUtils.getUserRoles().contains(SecurityUtils.ROLE_TRANSPORT)){
                    add(new MenuItemInfo("My Drivers", new Icon(VaadinIcon.MALE), DriverViewImpl.class));
                }
				add(new MenuItemInfo("Goods Offers", new Icon(VaadinIcon.PACKAGE), GoodsViewImpl.class));
				add(new MenuItemInfo("Transport Offers", new Icon(VaadinIcon.TRUCK), TransportViewImpl.class));
			}else {
				add(new MenuItemInfo("About Transport You", new Icon(VaadinIcon.GLOBE), AboutView.class));
			}
        }};

        List<RouterLink> links = new ArrayList<>();
        for (MenuItemInfo menuItemInfo : menuItems) {
            links.add(createLink(menuItemInfo));

        }
        return links;
    }

    private static RouterLink createLink(MenuItemInfo menuItemInfo) {
        RouterLink link = new RouterLink();
        link.addClassNames("flex", "mx-s", "p-s", "relative", "text-secondary");
        link.setRoute(menuItemInfo.getView());

        Span text = new Span(menuItemInfo.getText());
        text.addClassNames("font-medium", "text-s");

        if (menuItemInfo.getIconClass() != null) {
            Icon icon = menuItemInfo.getIconClass();
            icon.addClassNames("me-s", "text-l");
            Span iconSpan = new Span(icon);


            link.add(icon, text);
        }else {
            link.add(text);
        }

        return link;
    }

    private Footer createFooter() {
        Footer layout = new Footer();
        layout.addClassNames("flex", "items-center", "my-s", "px-m", "py-xs");

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
    
}
