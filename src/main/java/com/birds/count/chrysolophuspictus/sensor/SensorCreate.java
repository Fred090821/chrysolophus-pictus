package com.birds.count.chrysolophuspictus.sensor;

import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * SensorCreate serves as a data transfer object between
 * this object will be used to create and persist a valid Sensor Object.
 */
@NoArgsConstructor
public class SensorCreate {

    private String type;
    private String status;
    private Double latitude;
    private Double longitude;
    private String ipAddress;
    private String city;

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(final Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(final Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SensorCreate that = (SensorCreate) o;

        return new EqualsBuilder()
                .append(type, that.type)
                .append(status, that.status)
                .append(latitude, that.latitude)
                .append(longitude, that.longitude)
                .append(ipAddress, that.ipAddress)
                .append(city, that.city)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(type)
                .append(status)
                .append(latitude)
                .append(longitude)
                .append(ipAddress)
                .append(city)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "SensorCreate{" +
                "type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", ipAddress='" + ipAddress + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
