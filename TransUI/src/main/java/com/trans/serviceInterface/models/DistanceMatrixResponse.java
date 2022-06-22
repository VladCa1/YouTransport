package com.trans.serviceInterface.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class DistanceMatrixResponse {

    private List<String> destination_addresses;
    private List<String> origin_addresses;
    private List<DistanceMatrixInfo> rows;
    private String status;
    @Getter
    @Setter
    private String date;

    public DistanceMatrixResponse() {
    }

    public DistanceMatrixResponse(List<String> destination_addresses, List<String> origin_addresses, List<DistanceMatrixInfo> rows, String status) {
        this.destination_addresses = destination_addresses;
        this.origin_addresses = origin_addresses;
        this.rows = rows;
        this.status = status;
    }

    public List<String> getDestination_addresses() {
        return destination_addresses;
    }

    public void setDestination_addresses(List<String> destination_addresses) {
        this.destination_addresses = destination_addresses;
    }

    public List<String> getOrigin_addresses() {
        return origin_addresses;
    }

    public void setOrigin_addresses(List<String> origin_addresses) {
        this.origin_addresses = origin_addresses;
    }

    public List<DistanceMatrixInfo> getRows() {
        return rows;
    }

    public void setRows(List<DistanceMatrixInfo> rows) {
        this.rows = rows;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



}

