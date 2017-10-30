package com.healthiera.mobile.entity;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.healthiera.mobile.entity.enumeration.MeasurementType;

/**
 * @author Yengibar Manasyan
 * @date 17.10.2016
 */
@Table(name = "measurement")
public class Measurement extends Model {

    @Column(name = "schedule_id", notNull = true)
    private Schedule schedule;

    @Column(name = "goal_id")
    private Goal goal;

    @Column(name = "type", notNull = true)
    private MeasurementType type;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    public Measurement() {
        super();
    }

    public Measurement(Schedule schedule, Goal goal, MeasurementType type, String name, String description) {
        super();
        this.schedule = schedule;
        this.goal = goal;
        this.type = type;
        this.name = name;
        this.description = description;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    public MeasurementType getType() {
        return type;
    }

    public void setType(MeasurementType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
