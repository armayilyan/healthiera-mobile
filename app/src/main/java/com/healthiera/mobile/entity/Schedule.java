package com.healthiera.mobile.entity;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.healthiera.mobile.entity.enumeration.DurationType;
import com.healthiera.mobile.entity.enumeration.EventType;
import com.healthiera.mobile.entity.enumeration.RepeatType;

import java.util.Date;

/**
 * @author Yengibar Manasyan
 * @date 17.10.2016
 */
@Table(name = "schedule")
public class Schedule extends Model {

    @Column(name = "start_date", notNull = true)
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "duration_type", notNull = true)
    private DurationType durationType;

    @Column(name = "number_of_repetition")
    private Integer numberOfRepetition;

    @Column(name = "repeat_type", notNull = true)
    private RepeatType repeatType;

    @Column(name = "days_of_week")
    private String daysOfWeek;

    @Column(name = "days_interval")
    private Integer daysInterval;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "location")
    private String location;

    @Column(name = "remind_before_minutes", notNull = true)
    private Integer remindBeforeMinutes;

    @Column(name = "event_type", notNull = true)
    private EventType eventType;

    public Schedule() {
        super();
    }

    public Schedule(Date startDate, Date endDate, DurationType durationType, Integer numberOfRepetition,
                    RepeatType repeatType, String daysOfWeek, Integer daysInterval, String title,
                    String description, String location, Integer remindBeforeMinutes, EventType eventType) {
        super();
        this.startDate = startDate;
        this.endDate = endDate;
        this.durationType = durationType;
        this.numberOfRepetition = numberOfRepetition;
        this.repeatType = repeatType;
        this.daysOfWeek = daysOfWeek;
        this.daysInterval = daysInterval;
        this.title = title;
        this.description = description;
        this.location = location;
        this.remindBeforeMinutes = remindBeforeMinutes;
        this.eventType = eventType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public DurationType getDurationType() {
        return durationType;
    }

    public void setDurationType(DurationType durationType) {
        this.durationType = durationType;
    }

    public Integer getNumberOfRepetition() {
        return numberOfRepetition;
    }

    public void setNumberOfRepetition(Integer numberOfRepetition) {
        this.numberOfRepetition = numberOfRepetition;
    }

    public RepeatType getRepeatType() {
        return repeatType;
    }

    public void setRepeatType(RepeatType repeatType) {
        this.repeatType = repeatType;
    }

    public String getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(String daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public Integer getDaysInterval() {
        return daysInterval;
    }

    public void setDaysInterval(Integer daysInterval) {
        this.daysInterval = daysInterval;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getRemindBeforeMinutes() {
        return remindBeforeMinutes;
    }

    public void setRemindBeforeMinutes(Integer remindBeforeMinutes) {
        this.remindBeforeMinutes = remindBeforeMinutes;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }
}
