package com.healthiera.mobile.entity;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;

/**
 * @author Davit Ter-Arakelyan
 * @date 10/17/16
 */
@Table(name = "medication_log")
public class MedicationLog extends Model {

    @Column(name = "medication_id", notNull = true)
    private Medication medication;

    @Column(name = "log_date_time", notNull = true)
    private Date logDateTime;

    @Column(name = "schedule_date_time", notNull = true)
    private Date scheduleDateTime;

    public MedicationLog() {
        super();
    }

    public MedicationLog(Medication medication, Date logDateTime, Date scheduleDateTime) {
        super();
        this.medication = medication;
        this.logDateTime = logDateTime;
        this.scheduleDateTime = scheduleDateTime;
    }

    public Medication getMedication() {
        return medication;
    }

    public void setMedication(Medication medication) {
        this.medication = medication;
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
