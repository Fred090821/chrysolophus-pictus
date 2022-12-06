package com.birds.count.chrysolophuspictus.metric;

import com.birds.count.chrysolophuspictus.exception.ResourceNotFoundException;
import com.birds.count.chrysolophuspictus.sensor.Sensor;
import com.birds.count.chrysolophuspictus.sensor.SensorRepository;
import com.birds.count.chrysolophuspictus.sensor.SensorStatus;
import com.birds.count.chrysolophuspictus.util.DateRange;
import com.birds.count.chrysolophuspictus.util.Range;
import com.birds.count.chrysolophuspictus.wrapper.StatisticsWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Metric Service manages all processing for metrics
 */
@Service
public class MetricService {

    private final Logger logger = LoggerFactory.getLogger(MetricService.class);

    private final MetricRepository metricRepository;
    private final SensorRepository sensorRepository;

    @Autowired
    public MetricService(MetricRepository metricRepository, SensorRepository sensorRepository) {
        this.metricRepository = metricRepository;
        this.sensorRepository = sensorRepository;
    }

    /**
     * Method to retrieve all metrics and group them per sensor id
     *
     * @param id sensor which id will be used to retrieved its metric
     * @return Map<Integer, List < Metric>> - a map containing the metrics grouped by sensor id
     */
    public Map<Integer, List<Metric>> getAllMetricsForSensor(@Nullable Integer id) {

        final List<Metric> listMetric = metricRepository.findAllPerSensor(id);

        if (listMetric.isEmpty()) {
            return Collections.emptyMap();
        } else {
            return listMetric.stream()
                    .collect(Collectors.groupingBy(
                            Metric::getSensorId
                    ));
        }
    }

    /**
     * Method used to bulk persist assumption is that bulk not large
     *
     * @param sensorId - the id of the sensor which owns the metrics
     * @param metrics  - List of metrics from sensor with sensorId
     * @return Map<String, List < Metric>>
     * @throws ResourceNotFoundException - thrown when resource not found
     */
    public Map<String, List<Metric>> batchUpdate(final Integer sensorId, final List<Metric> metrics) throws ResourceNotFoundException {
        logger.info("bulk insertion with Sensor ::: {} and metric size ::: {}", sensorId, metrics.size());

        final List<Metric> createdMetrics = new ArrayList<>();
        final List<Metric> failedMetrics = new ArrayList<>();

        //invalid request if sensor not active
        Optional<Sensor> optionalSensor = sensorRepository.findById(sensorId);
        if (optionalSensor.isEmpty() || (optionalSensor.isPresent() && !optionalSensor.get().getStatus().equals(SensorStatus.ACTIVE))) {
            throw new ResourceNotFoundException("sensor not found or sensor not active");
        }

        //Many to one relationship between metrics and sensor
        for (Metric metric : metrics) {
            Metric savedMetric = null;
            metric.setSensorId(optionalSensor.get().getId());
            try {
                savedMetric = metricRepository.save(metric);
                createdMetrics.add(savedMetric);
            } catch (Exception e) {
                failedMetrics.add(savedMetric);
            }
        }
        return Map.of("created", createdMetrics, "failed", failedMetrics);
    }

    /**
     * If the Sensor is not provided then the list contain stats for each Sensor independently
     *
     * @param range - provide the date range required for the filtering and aggregation
     * @param id    - of the sensor, if provided will return only the metric for that sensor
     * @return List<StatisticsWrapper> -  a list of custom object containing stats
     */
    public List<StatisticsWrapper> getHumidityAndTemperatureStatistics(String range, Integer id) {
        logger.info("Get statistics of metric temperature and humidity");

        List<Metric> metrics;
        DateRange dateRange;

        if (null == range || range.isBlank()) {
            logger.info("No date range provided default range of last 15 minutes will apply");
            dateRange = DateRange.getDateRange(Range.MINUTES);
        } else {
            logger.info("Date range with value {} provided ", range);
            dateRange = DateRange.getDateRange(Range.valueOf(range.toUpperCase()));
        }

        metrics = metricRepository.findByCreatedAtBetweenAndSensorId(dateRange.getFrom(),
                dateRange.getTo(), id);

        return generateStatisticsWrapper(metrics, dateRange.getFrom(), dateRange.getTo());
    }

    /**
     * Generate stats from metrics collected within given range
     *
     * @param metricList - the collection of metric within range selected
     * @param from       - the start timestamp from which the data is collected and processed
     * @param to         - the end timestamp from which the data is collected and processed
     * @return List<StatisticsWrapper> - each sensor has its stats in the StatisticsWrapper
     */
    private List<StatisticsWrapper> generateStatisticsWrapper(@NonNull List<Metric> metricList, @NonNull Timestamp from, @NonNull Timestamp to) {
        return new ArrayList<>(
                metricList.stream()
                        .collect(Collectors.groupingBy(Metric::getSensorId,
                                Collectors.collectingAndThen(
                                        Collectors.toList(), a ->
                                                //values will always be present due to previous validation
                                                //so value -1 will not be reached but it is best practice to check
                                                //if value present with optional
                                                new StatisticsWrapper(
                                                        // the id of the sensor which stats is being generated
                                                        a.get(0).getSensorId(),
                                                        // average for humidity for a given sensor
                                                        a.stream().mapToDouble(Metric::getHumidity).average().isPresent() ? a.stream().mapToDouble(Metric::getHumidity).average().getAsDouble() : -1,
                                                        // average for temperature for a given sensor
                                                        a.stream().mapToDouble(Metric::getTemperature).average().isPresent() ? a.stream().mapToDouble(Metric::getTemperature).average().getAsDouble() : -1,
                                                        // max for humidity for a given sensor
                                                        a.stream().mapToDouble(Metric::getHumidity).max().isPresent() ? a.stream().mapToDouble(Metric::getHumidity).max().getAsDouble() : -1,
                                                        // max for temperature     for a given sensor
                                                        a.stream().mapToDouble(Metric::getTemperature).max().isPresent() ? a.stream().mapToDouble(Metric::getTemperature).max().getAsDouble() : -1,
                                                        //starting range for the collected metrics
                                                        from,
                                                        //end range for the collected metrics
                                                        to
                                                )
                                )))
                        .values());
    }

}
