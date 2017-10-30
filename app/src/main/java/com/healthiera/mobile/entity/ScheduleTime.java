package com.healthiera.mobile.entity;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * @author Yengibar Manasyan
 * @date 05.08.2016
 */
@Table(name = "schedule_time")
public class ScheduleTime extends Model {

    @Column(name = "schedule_id", notNull = true)
    private Schedule schedule;

    @Column(name = "time")
    private Integer time;

    public ScheduleTime() {
        super();
    }

    public ScheduleTime(Schedule schedule, Integer time) {
        super();
        this.schedule = schedule;
        this.time = time;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }
}
