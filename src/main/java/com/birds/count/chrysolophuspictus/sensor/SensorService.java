package com.birds.count.chrysolophuspictus.sensor;

import com.birds.count.chrysolophuspictus.exception.InvalidInvalidSensorRequestException;
import com.birds.count.chrysolophuspictus.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class that interfaces with sensor repository
 * It validate and format requests from controller to repository
 */
@Service
public class SensorService {

    private final Logger logger = LoggerFactory.getLogger(SensorService.class);

    private final SensorRepository sensorRepository;

    public SensorService(final SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    /**
     * service method to retrieve all sensors or a sensor with id = sensorId
     *
     * @param sensorId - the sensor to be retrieved id if provided
     * @return List<Sensor> - list of sensors if no id provided or a sensor if id provided
     */
    public List<Sensor> findAllSensors(@Nullable Integer sensorId) {
        return sensorRepository.findAllSensors(sensorId);
    }

    /**
     * service method to persist valid sensor entity
     *
     * @param request - SensorCreate object data provided by client
     * @return sensor - persisted valid object
     * @throws InvalidInvalidSensorRequestException - thrown when one or more parameter is invalid
     *                                              likely situation would be around validation of status
     */
    public Sensor create(@NonNull SensorCreate request) throws InvalidInvalidSensorRequestException {
        Sensor sensor = new Sensor();
        try {
            sensor.setType(request.getType());
            sensor.setStatus(SensorStatus.of(request.getStatus()));
            sensor.setLongitude(request.getLongitude());
            sensor.setLatitude(request.getLatitude());
            sensor.setIpAddress(request.getIpAddress());
            sensor.setCity(request.getCity());
        } catch (Exception ex) {
            throw new InvalidInvalidSensorRequestException("invalid argument provided or non unique parameter(s)");
        }

        return sensorRepository.save(sensor);
    }

    /**
     * service method to persist valid sensor entity
     *
     * @param sensorPatch - object containing data to partially update the given sensor
     * @param id          - the id of the sensor that needs partial update
     * @return Sensor the partially updated sensor
     * @throws ResourceNotFoundException
     */
    public Sensor patch(@NonNull SensorPatch sensorPatch, Integer id) throws ResourceNotFoundException, InvalidInvalidSensorRequestException {
        logger.info("Updating sensor with id ::: {}", id);

        Optional<Sensor> optionalSensor = sensorRepository.findById(id);
        if (optionalSensor.isEmpty()) {
            throw new ResourceNotFoundException("sensor not found");
        } else {
            Sensor sensor = optionalSensor.get();
            sensor.setStatus(SensorStatus.of(sensorPatch.getStatus()));
            sensor.setLongitude(sensorPatch.getLongitude());
            sensor.setLatitude(sensorPatch.getLatitude());
            return sensorRepository.save(sensor);
        }

    }
}
