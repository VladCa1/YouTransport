package com.trans.serviceInterface.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

public class TransportFormResultDTO {

    @Getter
    @Setter
    Long id;

    @Getter
    @Setter
    String toLocationCountry;

    @Getter
    @Setter
    String toLocationCity;

    @Getter
    @Setter
    String fromLocationCountry;

    @Getter
    @Setter
    String fromLocationCity;

    @Getter
    @Setter
    String payType;

    @Getter
    @Setter
    Double payValue;

    @Getter
    @Setter
    String payCurrency;

    @Getter
    @Setter
    String description;

    @Getter
    @Setter
    LocalDate toDate;

    @Getter
    @Setter
    LocalDate fromDate;

    @Getter
    @Setter
    String distance;

    @Getter
    @Setter
    String duration;

    @Getter
    @Setter
    Integer maxAcceptedDistanceFromSource;

    @Getter
    @Setter
    DriverResultEntryDTO driver;

    @Getter
    @Setter
    private String vehicleType;

    @Getter
    @Setter
    private int horsePower;

    @Getter
    @Setter
    private Double tonnage;

    @Getter
    @Setter
    private String make;

    @Getter
    @Setter
    private String type;

    @Getter
    @Setter
    private LocalDate fabricationYear;

    @Getter
    @Setter
    private Boolean hazardousMaterialsAccepted;

    @Getter
    @Setter
    private Set<String> goodsTypes;

}
