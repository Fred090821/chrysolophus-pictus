package com.birds.count.chrysolophuspictus.metric;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface MetricRepository extends JpaRepository<Metric, Integer> {

    @Query("SELECT m FROM Metric m WHERE (:id is null or m.id = :id) and m.createdAt BETWEEN :start AND :end")
    List<Metric> findByCreatedAtBetweenAndSensorId(@Param("start") Timestamp start,
                                                   @Param("end") Timestamp end, @Nullable @Param("id") Integer id);

    @Query("SELECT m FROM Metric m WHERE (:sensorId is null or m.sensorId = :sensorId) ")
    List<Metric> findAllPerSensor(@Nullable @Param("sensorId") Integer sensorId);
}
