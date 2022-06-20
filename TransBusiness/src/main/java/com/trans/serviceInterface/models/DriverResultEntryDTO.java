package com.trans.serviceInterface.models;

import lombok.Getter;
import lombok.Setter;

public class DriverResultEntryDTO {

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String firstName;

    @Getter
    @Setter
    private String lastName;

    @Getter
    @Setter
    private String nationality;

    @Getter
    @Setter
    private int age;

    @Getter
    @Setter
    private int experience;

    @Getter
    @Setter
    private boolean assigned;

    @Getter
    @Setter
    private byte[] image;

}
