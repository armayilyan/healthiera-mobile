package com.healthiera.mobile.entity;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;

/**
 * @author Yengibar Manasyan
 * @date 10/17/16
 */
@Table(name = "measurement_log")
public class MeasurementLog extends Model {

    @Column(name = "measurement_id", notNull = true)
    private Measurement measurement;

    @Column(name = "value", notNull = true)
    private String value;

    @Column(name = "log_date_time", notNull = true)
    private Date logDateTime;

    @Column(name = "schedule_date_time", notNull = true)
    private Date scheduleDateTime;

    public MeasurementLog() {
        super();
    }

    public MeasurementLog(Measurement measurement, String value, Date logDateTime, Date scheduleDateTime) {
        super();
        this.measurement = measurement;
        this.value = value;
        this.logDateTime = logDateTime;
        this.scheduleDateTime=scheduleDateTime;
    }

    public Measurement getMeasurement() {
        return measurement;
    }

    public void setMeasurement(Measurement measurement) {
        this.measurement = measurement;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getLogDateTime() {
        return logDateTime;
    }

    public void setLogDateTime(Date logDateTime) {
        this.logDateTime = logDateTime;
    }

    public Date getScheduleDateTime() {
        return scheduleDateTime;
    }

    public void setScheduleDateTime(Date scheduleDateTime) {
        this.scheduleDateTime = scheduleDateTime;
    }
}
