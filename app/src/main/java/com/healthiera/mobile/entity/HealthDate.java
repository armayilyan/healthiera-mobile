package com.healthiera.mobile.entity;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.healthiera.mobile.entity.enumeration.HealthDateType;

import java.util.Date;

/**
 * @author Davit Ter-Arakelyan
 * @date 22.10.2016
 */
@Table(name = "health_date")
public class HealthDate extends Model {

    @Column(name = "health_date_type", index = true, notNull = true)
    private HealthDateType healthDateType;

    @Column(name = "value", notNull = true)
    private String value;

    @Column(name = "date", notNull = true)
    private Date date;

    public HealthDate() {
        super();
    }

    public HealthDate(HealthDateType healthDateType, String value, Date date) {
        super();
        this.healthDateType = healthDateType;
        this.value = value;
        this.date=date;
    }

    public HealthDateType getHealthDateType() {
        return healthDateType;
    }

    public void setHealthDateType(HealthDateType healthDateType) {
        this.healthDateType = healthDateType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
