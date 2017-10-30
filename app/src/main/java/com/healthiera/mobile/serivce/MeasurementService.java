package com.healthiera.mobile.serivce;

import com.activeandroid.query.Select;
import com.healthiera.mobile.entity.Measurement;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Yengibar Manasyan
 * @date 10/18/16
 */
public class MeasurementService {

    public Long createMeasurement(Measurement measurement) {
        assertThat(measurement).isNotNull();
        assertThat(measurement.getId()).isNull();
        assertThat(measurement.getType()).isNotNull();
        final Long id = measurement.save();
        assertThat(id).isNotNull().isGreaterThan(0L);

        return id;
    }

    public Measurement findMeasurementById(Long id) {
        assertThat(id).isNotNull().isGreaterThan(0L);
        final Measurement measurement = Measurement.load(Measurement.class, id);
        assertThat(measurement).isNotNull();

        return measurement;
    }

    public Measurement findMeasurementByScheduleId(Long id) {
        assertThat(id).isNotNull().isGreaterThan(0L);
        final Measurement measurement = new Select().from(Measurement.class).where("schedule_id = ?", id).executeSingle();
        assertThat(measurement).isNotNull();

        return measurement;
    }
}
