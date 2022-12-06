package com.birds.count.chrysolophuspictus.sensor;

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
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test-application.properties")
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class SensorControllerIT {

    private static final String REQUEST_BASE_URI = "/api";
    private static final String REQUEST_URI_WITH_ID = "/sensors?id=1";
    private static final String REQUEST_URI_ALL = "/sensors";
    private static final String PATCH_REQUEST_URI = "/sensors/1";
    private static final String UNKNOWN_ID_REQUEST_URI = "/sensors?id=12";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SensorService sensorService;

    @Autowired
    private SensorRepository sensorRepository;

    //Get existing sensor
    @Test
    public void getValidRequest_shouldReturn_validSensor() throws Exception {

        //Given
        //Prepare the test by saving a sensor before the call
        SensorCreate sensor = new SensorCreate();
        sensor.setType("Weather");
        sensor.setStatus("active");
        sensor.setLongitude(20.23);
        sensor.setLatitude(12.87);
        sensor.setIpAddress("86.12.43.1");
        sensor.setCity("Dublin");

        //Calling the service to save the sensor
        Sensor savedUser = sensorService.create(sensor);

        //When
        //hen
        var result = mockMvc.perform(get(REQUEST_BASE_URI + REQUEST_URI_WITH_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(is(equalTo(1))))
                .andExpect(jsonPath("$[0].status").value(is(equalTo(SensorStatus.ACTIVE.toString()))))
                .andExpect(jsonPath("$[0].longitude").value(is(equalTo(20.23))))
                .andExpect(jsonPath("$[0].latitude").value(is(not(equalTo(20.23)))))
                .andExpect(jsonPath("$[0].latitude").value(is(equalTo(12.87))))
                //change the latitude
                .andExpect(jsonPath("$[0].latitude").value(is(not(equalTo(12.871)))))
                .andExpect(jsonPath("$[0].ipAddress").value(is(equalTo("86.12.43.1"))))
                .andExpect(jsonPath("$[0].city").value(is(equalTo("Dublin"))))
                //change the city
                .andExpect(jsonPath("$[0].city").value(is(not(equalTo("Cork")))))
                .andExpect(jsonPath("$[0].type").value(is(equalTo("Weather")))).andReturn();

        assertFalse(StringUtils.isEmpty(result.getResponse().getContentAsString()));
    }

    //Get a non existing sensor return not found
    @Test
    public void getInvalidRequest_shouldReturn_badRequest() throws Exception {

        //Given
        //When
        //Then
        var result = mockMvc.perform(get(REQUEST_BASE_URI + UNKNOWN_ID_REQUEST_URI))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();

        assertTrue(StringUtils.isEmpty(result.getResponse().getContentAsString()));
    }

    //POST a valid request
    @Test
    public void postValidRequest_ShouldReturn_isCreated() throws Exception {

        //Given
        var mapper = new ObjectMapper();
        SensorCreate sensor = new SensorCreate();
        sensor.setType("Weather");
        sensor.setStatus("active");
        sensor.setLongitude(201.23);
        sensor.setLatitude(121.87);
        sensor.setIpAddress("86.12.43.11");
        sensor.setCity("Dublin");

        //When
        //Then
        //create new sensor
        var result = mockMvc
                .perform(post(REQUEST_BASE_URI + REQUEST_URI_WITH_ID).accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(sensor)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(is(equalTo(1))))
                .andExpect(jsonPath("$.status").value(is(equalTo("ACTIVE"))))
                .andExpect(jsonPath("$.longitude").value(is(equalTo(201.23))))
                .andExpect(jsonPath("$.latitude").value(is(not(equalTo(120.23)))))
                .andExpect(jsonPath("$.latitude").value(is(equalTo(121.87))))
                //change the latitude
                .andExpect(jsonPath("$.latitude").value(is(not(equalTo(12.871)))))
                .andExpect(jsonPath("$.ipAddress").value(is(equalTo("86.12.43.11"))))
                .andExpect(jsonPath("$.city").value(is(equalTo("Dublin"))))
                //change the city
                .andExpect(jsonPath("$.city").value(is(not(equalTo("Cork")))))
                .andExpect(jsonPath("$.type").value(is(equalTo("Weather")))).andReturn();

        assertFalse(StringUtils.isEmpty(result.getResponse().getContentAsString()));
    }

    //Post an invalid request
    @Test
    public void invalidRequest_ShouldReturn_BadRequest() throws Exception {

        //Given
        //Set invalid sensor to be posted
        var mapper = new ObjectMapper();
        SensorCreate sensor = new SensorCreate();
        sensor.setType("Weather");
        // invalid entry -> bad request
        //should be a valid enum valid
        sensor.setStatus("bad");
        sensor.setLongitude(201.23);
        sensor.setLatitude(121.87);
        sensor.setIpAddress("86.12.43.11");
        sensor.setCity("Dublin");

        //When
        //Then
        //post invalid object return bad request
        var result = mockMvc
                .perform(post(REQUEST_BASE_URI + REQUEST_URI_ALL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(sensor)))
                .andExpect(status().isBadRequest()).andReturn();

        //empty object return
        assertTrue(StringUtils.isEmpty(result.getResponse().getContentAsString()));
    }

    //Get all sensor test
    @Test
    public void getValidRequestWithoutId_shouldReturn_List() throws Exception {

        //Given
        SensorCreate sensor = new SensorCreate();
        sensor.setType("Weather");
        sensor.setStatus("active");
        sensor.setLongitude(20.23);
        sensor.setLatitude(12.87);
        sensor.setIpAddress("86.12.43.1");
        sensor.setCity("Dublin");
        Sensor firstCreatedUser = sensorService.create(sensor);

        SensorCreate sensorSecond = new SensorCreate();
        sensorSecond.setType("Weather");
        sensorSecond.setStatus("active");
        sensorSecond.setLongitude(20.23);
        sensorSecond.setLatitude(12.87);
        sensorSecond.setIpAddress("86.12.43.1");
        sensorSecond.setCity("Dublin");
        Sensor secondCreatedUser = sensorService.create(sensorSecond);

        //When
        //Then
        var result = mockMvc.perform(get(REQUEST_BASE_URI + REQUEST_URI_ALL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(equalTo(2))))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertFalse(StringUtils.isEmpty(result.getResponse().getContentAsString()));
    }

    //Patch
    @Test
    public void patchValidSensor_shouldReturn_patchedSensor() throws Exception {

        //Given
        //set object that will be patched
        var mapper = new ObjectMapper();
        SensorCreate sensor = new SensorCreate();
        sensor.setType("Weather");
        sensor.setStatus("active");
        sensor.setLongitude(20.23);
        sensor.setLatitude(12.87);
        sensor.setIpAddress("86.12.43.1");
        sensor.setCity("Dublin");

        //When
        //Post the set object
        var resultPost = mockMvc
                .perform(post(REQUEST_BASE_URI + REQUEST_URI_ALL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(sensor)))
                .andExpect(status().isCreated()).andReturn();

        //Patcher object
        SensorPatch sensorPatch = new SensorPatch(100.23, 100.23, "inactive");

        //Then
        //Patch the previously Posted sensor
        MvcResult resultPatch = mockMvc
                .perform(patch(REQUEST_BASE_URI + PATCH_REQUEST_URI)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(sensorPatch)))
                .andExpect(jsonPath("$.id").value(is(equalTo(1))))
                .andExpect(jsonPath("$.status").value(is(equalTo(SensorStatus.INACTIVE.toString()))))
                .andExpect(jsonPath("$.longitude").value(is(equalTo(100.23))))
                .andExpect(jsonPath("$.latitude").value(is(equalTo(100.23))))
                .andExpect(jsonPath("$.ipAddress").value(is(equalTo("86.12.43.1"))))
                .andExpect(jsonPath("$.city").value(is(equalTo("Dublin"))))
                .andExpect(jsonPath("$.type").value(is(equalTo("Weather"))))
                .andReturn();

        assertFalse(StringUtils.isEmpty(resultPatch.getResponse().getContentAsString()));
    }

}
