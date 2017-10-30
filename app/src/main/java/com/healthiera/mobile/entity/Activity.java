package com.healthiera.mobile.entity;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;

/**
 * @author Yengibar Manasyan
 * @date 10/20/16
 */
public class Activity extends Model {

    @Column(name = "schedule_id", notNull = true)
    private Schedule schedule;

    @Column(name = "name")
    private String name;

    @Column(name = "theme")
    private String theme;

    public Activity() {
        super();
    }

    public Activity(Schedule schedule, String name, String theme) {
        super();
        this.schedule = schedule;
        this.name = name;
        this.theme = theme;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}
