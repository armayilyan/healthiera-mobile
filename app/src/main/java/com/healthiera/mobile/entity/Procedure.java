package com.healthiera.mobile.entity;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * @author Yengibar Manasyan
 * @date 17.10.2016
 */
@Table(name = "procedure")
public class Procedure extends Model {

    @Column(name = "schedule_id", notNull = true)
    private Schedule schedule;

    @Column(name = "doctor_id", notNull = true)
    private Doctor doctor;

    @Column(name = "code")
    private String code;

    @Column(name = "description")
    private String description;

    public Procedure() {
        super();
    }

    public Procedure(Schedule schedule, Doctor doctor, String code, String description) {
        super();
        this.schedule = schedule;
        this.doctor = doctor;
        this.code = code;
        this.description = description;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
