package com.birds.count.chrysolophuspictus.metric;

import com.birds.count.chrysolophuspictus.sensor.Sensor;
import com.birds.count.chrysolophuspictus.sensor.SensorCreate;
import com.birds.count.chrysolophuspictus.sensor.SensorRepository;
import com.birds.count.chrysolophuspictus.sensor.SensorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test-application.properties")
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MetricControllerTest {

    private static final String REQUEST_BASE_URI = "/api";
    private static final String REQUEST_URI = "/metrics/sensor";
    private static final String REQUEST_URI_FOR_STATS_DAY = "/metrics/stats?range=today";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SensorService sensorService;

    @Autowired
    private SensorRepository sensorRepository;


    //POST sensor post metrics
    @Test
    public void sensorPostMetric_shouldReturn__isCreated() throws Exception {

        //Given
        //Post metrics from sensor with id = 1
        SensorCreate sensor = new SensorCreate();
        sensor.setType("Weather");
        sensor.setStatus("active");
        sensor.setLongitude(20.23);
        sensor.setLatitude(12.87);
        sensor.setIpAddress("86.11.43.1");
        sensor.setCity("Dublin");
        Sensor savedSensor = sensorService.create(sensor);

        var mapper = new ObjectMapper();

        Metric metric = new Metric();
        metric.setName("metric1");
        metric.setTemperature(12.25);
        metric.setHumidity(20.23);
        metric.setSensorId(11);
        metric.setWindSpeed(23);
        metric.setWindDirection(32);

        Metric metricSecond = new Metric();
        metricSecond.setName("metric2");
        metricSecond.setTemperature(12.5);
        metricSecond.setHumidity(201.23);
        metricSecond.setSensorId(11);
        metricSecond.setWindSpeed(13);
        metricSecond.setWindDirection(32);

        final List<Metric> metrics = List.of(metric, metricSecond);

        //When
        //Then
        //create new object
        var result = mockMvc
                .perform(post(REQUEST_BASE_URI + REQUEST_URI + "/" + savedSensor.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(metrics)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertTrue(StringUtils.isNotEmpty(result.getResponse().getContentAsString()));

    }

    /**
     * We create 2 sensors with 2 metrics each and save to db
     * Then we make a call to retrieve the stats
     */
    @Test
    public void getValidRequestWithoutId_shouldReturn_List() throws Exception {

        //Given
        var mapper = new ObjectMapper();

        //1 - First sensor and its metrics
        SensorCreate sensor = new SensorCreate();
        sensor.setType("Weather");
        sensor.setStatus("active");
        sensor.setLongitude(10.10);
        sensor.setLatitude(10.10);
        sensor.setIpAddress("86.12.43.1");
        sensor.setCity("Dublin");
        Sensor firstSavedSensor = sensorService.create(sensor);

        Metric firstSensorMetric1 = new Metric();
        firstSensorMetric1.setName("metric1");
        firstSensorMetric1.setTemperature(10.10);
        firstSensorMetric1.setHumidity(10.10);
        firstSensorMetric1.setSensorId(10);
        firstSensorMetric1.setWindSpeed(10);
        firstSensorMetric1.setWindDirection(10);

        Metric firstSensorMetric2 = new Metric();
        firstSensorMetric2.setName("metric2");
        firstSensorMetric2.setTemperature(10.10);
        firstSensorMetric2.setHumidity(10.10);
        firstSensorMetric2.setSensorId(10);
        firstSensorMetric2.setWindSpeed(10);
        firstSensorMetric2.setWindDirection(10);

        List<Metric> firstMetricsList = List.of(firstSensorMetric1, firstSensorMetric2);

        //save first sensor's metrics to db
        var result = mockMvc
                .perform(post(REQUEST_BASE_URI + REQUEST_URI + "/" + firstSavedSensor.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(firstMetricsList)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();


        //And this
        //2 - Second sensor with saved metrics
        SensorCreate secondSensor = new SensorCreate();
        secondSensor.setType("Weather");
        secondSensor.setStatus("active");
        secondSensor.setLongitude(20.20);
        secondSensor.setLatitude(20.20);
        secondSensor.setIpAddress("86.12.4.1");
        secondSensor.setCity("Dublin");
        Sensor secondSavedSensor = sensorService.create(secondSensor);


        mapper = new ObjectMapper();

        Metric secondSensorMetric1 = new Metric();
        secondSensorMetric1.setName("metric1");
        secondSensorMetric1.setTemperature(30.30);
        secondSensorMetric1.setHumidity(30.30);
        secondSensorMetric1.setSensorId(30);
        secondSensorMetric1.setWindSpeed(30);
        secondSensorMetric1.setWindDirection(30);

        Metric secondSensorMetric2 = new Metric();
        secondSensorMetric2.setName("metric2");
        secondSensorMetric2.setTemperature(10.10);
        secondSensorMetric2.setHumidity(10.10);
        secondSensorMetric2.setSensorId(10);
        secondSensorMetric2.setWindSpeed(10);
        secondSensorMetric2.setWindDirection(10);

        List<Metric> secondMetricsList = List.of(secondSensorMetric1, secondSensorMetric2);

        //save second sensor metrics to db
        result = mockMvc
                .perform(post(REQUEST_BASE_URI + REQUEST_URI + "/" + secondSavedSensor.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(secondMetricsList)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        //When
        //Then
        //calculate the metrics result visible in the logs
        result = mockMvc.perform(get(REQUEST_BASE_URI + REQUEST_URI_FOR_STATS_DAY))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(equalTo(2))))
                //average and max for metrics of first sensor
                .andExpect(jsonPath("$[0].averageHumidity", equalTo(10.1)))
                .andExpect(jsonPath("$[0].averageTemperature", equalTo(10.1)))
                .andExpect(jsonPath("$[0].maxHumidity", equalTo(10.1)))
                .andExpect(jsonPath("$[0].maxTemperature", equalTo(10.1)))
                //average and max of metrics of second sensor
                .andExpect(jsonPath("$[1].averageHumidity", equalTo(20.2)))
                .andExpect(jsonPath("$[1].averageTemperature", equalTo(20.2)))
                .andExpect(jsonPath("$[1].maxHumidity", equalTo(30.3)))
                .andExpect(jsonPath("$[1].maxTemperature", equalTo(30.3)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertTrue(StringUtils.isNotEmpty(result.getResponse().getContentAsString()));

    }

}
