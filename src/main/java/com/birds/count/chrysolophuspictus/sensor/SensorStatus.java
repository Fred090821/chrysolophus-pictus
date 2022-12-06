package com.birds.count.chrysolophuspictus.sensor;

import com.birds.count.chrysolophuspictus.exception.InvalidInvalidSensorRequestException;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * SensorStatus class used to enforce sensor status validation
 */
public enum SensorStatus {
    ACTIVE("active"), INACTIVE("inactive"), DISABLED("disabled"), DECOMMISSIONED("decommissioned");

    private final String status;

    SensorStatus(final String status) {
        this.status = status;
    }

    /**
     * Utility method to validate a status and set the default value
     *
     * @param status - input value provided by the client as part of persisting the sensor
     * @return a valid SensorStatus or the default Active status.
     */
    public static SensorStatus of(final String status) throws InvalidInvalidSensorRequestException {

        Optional<SensorStatus> first =
                Stream.of(SensorStatus.values())
                        .filter(p -> p.getStatus().equalsIgnoreCase(status))
                        .findFirst();
        first.orElseThrow(
                () -> new InvalidInvalidSensorRequestException("Invalid status provided"));

        return first.get();
    }

    public String getStatus() {
        return status;
    }
}