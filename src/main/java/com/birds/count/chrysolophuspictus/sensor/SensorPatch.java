package com.birds.count.chrysolophuspictus.sensor;

import org.springframework.lang.NonNull;

/**
 * Model to hold the values for partial updating a sensor
 */
public class SensorPatch {

    //New status value
    private String status;
    //New latitude position value
    private Double latitude;
    //New longitude position value
    private Double longitude;


    public SensorPatch() {
    }

    //mandatory attributes
    public SensorPatch(@NonNull final Double latitude, @NonNull final Double longitude, @NonNull final String status) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "SensorPatch: " + this.status + ", " + this.latitude + ", " + this.longitude;
    }

}