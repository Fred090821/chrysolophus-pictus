package com.birds.count.chrysolophuspictus.sensor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;

//Sensor repository to handle all sensor queries
@Repository
public interface SensorRepository extends JpaRepository<Sensor, Integer> {

    @Query("SELECT s FROM Sensor s WHERE (:sensorId is null or s.id = :sensorId) ")
    List<Sensor> findAllSensors(@Nullable @Param("sensorId") Integer sensorId);


}
