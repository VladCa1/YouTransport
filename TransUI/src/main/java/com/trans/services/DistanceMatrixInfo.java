package com.trans.services;

import java.util.List;

public class DistanceMatrixInfo {

    private List<DistanceMatrixElements> elements;

    public DistanceMatrixInfo(List<DistanceMatrixElements> elements) {
        this.elements = elements;
    }

    public DistanceMatrixInfo() {
    }

    public List<DistanceMatrixElements> getElements() {
        return elements;
    }

    public void setElements(List<DistanceMatrixElements> elements) {
        this.elements = elements;
    }

}
