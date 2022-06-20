package com.trans.views.drivers;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import lombok.Getter;

import javax.annotation.PostConstruct;
import java.io.InputStream;

public class NewDriverForm extends VerticalLayout {

    @Getter
    private TextField firstName;

    @Getter
    private TextField lastName;

    @Getter
    private IntegerField age;

    @Getter
    private IntegerField experience;

    @Getter
    private TextField nationality;

    @Getter
    private Upload imageUpload;

    @Getter
    private InputStream fileData;

    @Getter
    private Button createDriverButton;

    public NewDriverForm() {
        firstName = new TextField("First Name:");
        lastName = new TextField("Last Name:");
        age = new IntegerField("Age");
        experience = new IntegerField("Experience (yrs):");
        nationality = new TextField("Nationality:");

        MemoryBuffer memoryBuffer = new MemoryBuffer();
        imageUpload = new Upload();
        imageUpload.setReceiver(memoryBuffer);
        imageUpload.setDropLabel(new Label("Upload driver image:"));
        imageUpload.setAcceptedFileTypes("image/png");
        imageUpload.addSucceededListener(event ->{
            fileData = memoryBuffer.getInputStream();
        });

        createDriverButton = new Button("Create");
        createDriverButton.setIcon(new Icon(VaadinIcon.PLUS));

        setMaxWidth("500px");
        getStyle().set("margin", "0 auto");
        getStyle().set("margin-top", "40px");
        getStyle().set("border", "1px solid lightgray");

        this.add(firstName, lastName, age, experience, imageUpload, nationality, createDriverButton);

    }

}
