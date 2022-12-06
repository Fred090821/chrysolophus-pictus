package com.birds.count.chrysolophuspictus.wrapper;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

/**
 * Class that will hold statistics of
 * humidity and temperature for a given sensor
 */
public class StatisticsWrapper {

    private Integer sensorId;

    private Double averageHumidity;

    private Double averageTemperature;

    private Double maxHumidity;

    private Double maxTemperature;

    private Timestamp rangeFrom;

    private Timestamp rangeTo;

    public StatisticsWrapper() {
    }

    public StatisticsWrapper(Integer sensorId, Double averageHumidity, Double averageTemperature, Double maxHumidity, Double maxTemperature, Timestamp rangeFrom, Timestamp rangeTo) {
        super();
        this.sensorId = sensorId;
        this.averageHumidity = averageHumidity;
        this.averageTemperature = averageTemperature;
        this.maxHumidity = maxHumidity;
        this.maxTemperature = maxTemperature;
        this.rangeFrom = rangeFrom;
        this.rangeTo = rangeTo;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy HH:mm:ss")
    public Timestamp getRangeFrom() {
        return rangeFrom;
    }

    public void setRangeFrom(Timestamp rangeFrom) {
        this.rangeFrom = rangeFrom;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy HH:mm:ss")
    public Timestamp getRangeTo() {
        return rangeTo;
    }

    public void setRangeTo(Timestamp rangeTo) {
        this.rangeTo = rangeTo;
    }

    public Double getAverageHumidity() {
        return averageHumidity;
    }

    public void setAverageHumidity(Double averageHumidity) {
        this.averageHumidity = averageHumidity;
    }

    public Double getAverageTemperature() {
        return averageTemperature;
    }

    public void setAverageTemperature(Double averageTemperature) {
        this.averageTemperature = averageTemperature;
    }

    public Integer getSensorId() {
        return sensorId;
    }

    public void setSensorId(Integer sensorId) {
        this.sensorId = sensorId;
    }

    public Double getMaxHumidity() {
        return maxHumidity;
    }

    public void setMaxHumidity(Double maxHumidity) {
        this.maxHumidity = maxHumidity;
    }

    public Double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(Double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

}
