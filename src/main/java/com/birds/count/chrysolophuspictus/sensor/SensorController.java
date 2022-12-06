package com.birds.count.chrysolophuspictus.sensor;

import com.birds.count.chrysolophuspictus.exception.InvalidInvalidSensorRequestException;
import com.birds.count.chrysolophuspictus.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * Controller that handles requests to retrieve sensor(s)
 * Create a sensor
 * Partial Update a sensor
 * Assuming sensors data are not deleted for historical reasons
 */
@RestController
@RequestMapping("/api")
public class SensorController {

    private final Logger logger = LoggerFactory.getLogger(SensorController.class);

    private final SensorService sensorService;
    private Sensor sensor;

    public SensorController(final SensorService sensorService) {
        this.sensorService = sensorService;
    }

    /**
     * Get endpoint to retrieve all sensors or a sensor
     * with id = sensorId
     *
     * @param sensorId - the sensor to be retrieved id if provided
     * @return List<Sensor> - list of sensors if no id provided or
     * a sensor if id provided
     */
    @GetMapping("/sensors")
    public ResponseEntity<List<Sensor>> getSensors(@RequestParam(value = "id", required = false) Integer sensorId) {
        final List<Sensor> sensors = sensorService.findAllSensors(sensorId);
        if (sensors.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(sensors, HttpStatus.OK);
        }
    }

    /**
     * Post endpoint to Add a sensor
     *
     * @param request - provided parameter by the client to create a sensor
     * @return the sensor just created
     */
    @PostMapping("/sensors")
    public ResponseEntity<Sensor> createSensor(@RequestBody SensorCreate request) {

        try {
            sensor = sensorService.create(request);
        } catch (InvalidInvalidSensorRequestException ex) {
            logger.warn("Invalid input to create sensor with exception [ {} ] ::: {}", ex.getClass().getName(), ex);
        }

        if (Objects.nonNull(sensor)) {
            return new ResponseEntity<>(sensor, HttpStatus.CREATED);
        } else { //in the case the data does not meet validation
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Patch endpoint to partially update a sensor would apply in cases where
     * we need to change a sensor location or name
     *
     * @param partialUpdate - a builder object to hold data to apply
     * @param sensorId      - the sensor to update
     * @return sensor that has been partially updated
     */
    @PatchMapping("/sensors/{id}")
    public ResponseEntity<Sensor> partialUpdateName(
            @RequestBody SensorPatch partialUpdate, @PathVariable("id") Integer sensorId) {

        try {
            sensor = sensorService.patch(partialUpdate, sensorId);
        } catch (ResourceNotFoundException | InvalidInvalidSensorRequestException ex) {
            logger.debug("Sensor with id ::: {} was not found ", sensorId);
        }
        if (Objects.nonNull(sensor)) {
            return new ResponseEntity<>(sensor, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }

    }

}
