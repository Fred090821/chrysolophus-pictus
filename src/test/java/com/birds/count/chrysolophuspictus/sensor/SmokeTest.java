package com.birds.count.chrysolophuspictus.sensor;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//Make sure the environment is configure properly
@SpringBootTest
public class SmokeTest {

    @Autowired
    private SensorController controller;

    @Autowired
    private SensorRepository repository;

    @Autowired
    private SensorService service;

    @Test
    public void contextLoads() {
        Assertions.assertThat(controller).isNotNull();
        Assertions.assertThat(repository).isNotNull();
        Assertions.assertThat(service).isNotNull();
    }
}