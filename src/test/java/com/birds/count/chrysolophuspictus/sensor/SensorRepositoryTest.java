package com.birds.count.chrysolophuspictus.sensor;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource("classpath:test-application.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class SensorRepositoryTest {

    @Autowired
    private SensorRepository sensorRepository;

    //Repository should save valid sensor
    @Test
    public void repositorySavedSensor_shouldEqual_repositoryRetrievedSensor() {

        //Given
        Sensor sensor = new Sensor();
        sensor.setType("Weather");
        sensor.setStatus(SensorStatus.ACTIVE);
        sensor.setLongitude(20.23);
        sensor.setLatitude(12.87);
        sensor.setIpAddress("86.12.43.1");
        sensor.setCity("Dublin");

        //When
        Sensor savedUser = sensorRepository.save(sensor);

        //Then
        Assertions.assertThat(savedUser).isEqualTo(sensor);
    }

}
