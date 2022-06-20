package com.trans.security.data;

import lombok.Getter;
import lombok.Setter;

import java.awt.image.BufferedImage;

public class UserDetails {

    @Getter
    @Setter
    private String firstName;

    @Getter
    @Setter
    private String lastName;

    @Getter
    @Setter
    private String phoneNumber;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String companyDetails;

    @Getter
    @Setter
    private BufferedImage photoDetails;
}
