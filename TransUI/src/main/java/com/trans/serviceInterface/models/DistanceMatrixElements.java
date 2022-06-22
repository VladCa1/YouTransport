package com.trans.serviceInterface.models;

public class DistanceMatrixElements {

    private Distance distance;
    private DurationMatrix duration;
    private String status;

    public DistanceMatrixElements(Distance distance, DurationMatrix duration, String status) {
        this.distance = distance;
        this.duration = duration;

        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DistanceMatrixElements() {
    }

    public DurationMatrix getDuration() {
        return duration;
    }

    public void setDuration(DurationMatrix duration) {
        this.duration = duration;
    }

    public Distance getDistance() {
        return distance;
    }

    public void setDistance(Distance distance) {
        this.distance = distance;
    }

}

