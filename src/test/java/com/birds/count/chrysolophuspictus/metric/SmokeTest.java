package com.birds.count.chrysolophuspictus.metric;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//Make sure the environment is configure properly
@SpringBootTest
public class SmokeTest {

    @Autowired
    private MetricController controller;

    @Autowired
    private MetricRepository repository;

    @Autowired
    private MetricService service;

    @Test
    public void contextLoads() {
        Assertions.assertThat(controller).isNotNull();
        Assertions.assertThat(repository).isNotNull();
        Assertions.assertThat(service).isNotNull();
    }
}