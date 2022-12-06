package com.birds.count.chrysolophuspictus.sensor;

import com.birds.count.chrysolophuspictus.share.BaseEntity;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

//Model representing the remote device collecting weather metrics data
@NoArgsConstructor
@Entity
public class Sensor extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //type of the sensor
    @Column(nullable = false)
    private String type;

    //sensor status, ex active
    @Column(nullable = false)
    private SensorStatus status;

    //latitude for position/location
    @Column(nullable = false)
    private Double latitude;

    //longitude for position
    @Column(nullable = false)
    private Double longitude;

    //sensor ip address
    @Column(name = "ip_address", nullable = false)
    private String ipAddress;

    //city where sensor is located
    @Column(nullable = false)
    private String city;

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

    public Integer getId() {
        return id;
    }

    public String gettype() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public SensorStatus getStatus() {
        return status;
    }

    public void setStatus(final SensorStatus status) {
        this.status = status;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(final String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Sensor{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", status=" + status +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", ipAddress='" + ipAddress + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
