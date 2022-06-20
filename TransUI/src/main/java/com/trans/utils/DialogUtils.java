package com.trans.utils;

import com.trans.views.widgets.buttons.CancelButton;
import com.trans.views.widgets.buttons.OkButton;
import com.vaadin.componentfactory.EnhancedDialog;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class DialogUtils {

	public static Button confirm(String dialogText) {
		EnhancedDialog confirmDialog = new EnhancedDialog();
		confirmDialog.add(new Text(dialogText));
		confirmDialog.setCloseOnEsc(false);
		confirmDialog.setCloseOnOutsideClick(false);

		OkButton okButton = new OkButton(confirmEvent -> {
			confirmDialog.close();
		});

		okButton.getElement().getStyle().set("margin-left", "auto");

		CancelButton cancelButton = new CancelButton(cancelEvent -> {
			confirmDialog.close();
		});

		confirmDialog.add(new HorizontalLayout(cancelButton, okButton));
		confirmDialog.open();

		return okButton;
	}

	public static Button confirm(String dialogHeader, String dialogText) {
		EnhancedDialog confirmDialog = new EnhancedDialog();
		confirmDialog.add(new Text(dialogText));
		confirmDialog.setHeader(dialogHeader);
		confirmDialog.setCloseOnEsc(false);
		confirmDialog.setCloseOnOutsideClick(false);

		OkButton okButton = new OkButton(confirmEvent -> {
			confirmDialog.close();
		});

		okButton.getElement().getStyle().set("margin-left", "auto");

		CancelButton cancelButton = new CancelButton(cancelEvent -> {
			confirmDialog.close();
		});

		confirmDialog.add(new HorizontalLayout(cancelButton, okButton));
		confirmDialog.open();

		return okButton;
	}

	public static Dialog alert(String dialogText) {
		Dialog alertDialog = new Dialog();
		alertDialog.add(new Text(dialogText));
		alertDialog.setCloseOnEsc(false);
		alertDialog.setCloseOnOutsideClick(false);

		CancelButton cancelButton = new CancelButton(cancelEvent -> {
			alertDialog.close();
		});
		alertDialog.add(new HorizontalLayout(cancelButton));
		alertDialog.open();

		return alertDialog;
	}

	public static EnhancedDialog alert(String dialogHeader, String dialogText) {
		EnhancedDialog alertDialog = new EnhancedDialog();
		alertDialog.add(new Text(dialogText));
		alertDialog.setHeader(dialogHeader);
		alertDialog.setCloseOnEsc(false);
		alertDialog.setCloseOnOutsideClick(false);

		CancelButton cancelButton = new CancelButton(cancelEvent -> {
			alertDialog.close();
		});
		alertDialog.add(new HorizontalLayout(cancelButton));
		alertDialog.open();

		return alertDialog;
	}

	public static EnhancedDialog alert(String dialogHeader, Component component) {
		EnhancedDialog alertDialog = new EnhancedDialog();
		alertDialog.add(component);
		alertDialog.setHeader(dialogHeader);
		alertDialog.setCloseOnEsc(true);
		alertDialog.setCloseOnOutsideClick(true);

		CancelButton cancelButton = new CancelButton(cancelEvent -> {
			alertDialog.close();
		});
		alertDialog.add(new HorizontalLayout(cancelButton));
		alertDialog.open();

		return alertDialog;
	}


}
