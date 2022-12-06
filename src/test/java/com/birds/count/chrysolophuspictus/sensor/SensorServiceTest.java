package com.birds.count.chrysolophuspictus.sensor;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource("classpath:test-application.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class SensorServiceTest {

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private SensorService sensorService;

    @Test
    public void repositoryShouldFindSavedSensors() {

        //Given
        //Save first object so the id is 1
        Sensor sensor = new Sensor();
        sensor.setType("Weather");
        sensor.setStatus(SensorStatus.ACTIVE);
        sensor.setLongitude(20.23);
        sensor.setLatitude(12.87);
        sensor.setIpAddress("86.12.43.1");
        sensor.setCity("Dublin");
        Sensor savedUser = sensorRepository.save(sensor);

        //Save additional entities in group
        Sensor firstSensor = new Sensor();
        firstSensor.setType("Weather");
        firstSensor.setStatus(SensorStatus.ACTIVE);
        firstSensor.setLongitude(20.23);
        firstSensor.setLatitude(12.87);
        firstSensor.setIpAddress("86.12.43.1");
        firstSensor.setCity("Dublin");

        Sensor secondSensor = new Sensor();
        secondSensor.setType("Weather");
        secondSensor.setStatus(SensorStatus.ACTIVE);
        secondSensor.setLongitude(201.23);
        secondSensor.setLatitude(121.87);
        secondSensor.setIpAddress("86.12.43.11");
        secondSensor.setCity("Dublin");

        Sensor thirdSensor = new Sensor();
        thirdSensor.setType("Weather");
        thirdSensor.setStatus(SensorStatus.ACTIVE);
        thirdSensor.setLongitude(201.2);
        thirdSensor.setLatitude(121.8);
        thirdSensor.setIpAddress("86.1.43.1");
        thirdSensor.setCity("Dublin");

        List<Sensor> sensors = List.of(firstSensor, secondSensor, thirdSensor);
        List<Sensor> savedSensors = sensorRepository.saveAll(sensors);

        //When
        //Retrieve first saved entity with id = 1 and compare ignoring the id
        List<Sensor> retrievedSensors = sensorService.findAllSensors(1);
        Assertions.assertThat(retrievedSensors.get(0)).usingRecursiveComparison().ignoringFields("id").isEqualTo(sensor);

        retrievedSensors = sensorService.findAllSensors(null);

        //Then
        //retrieved sensors contains the sensors, list of 4 sensors
        Assertions.assertThat(retrievedSensors)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id").containsAll(sensors);

        Assertions.assertThat(retrievedSensors.size()).isEqualTo(4);

    }

}
