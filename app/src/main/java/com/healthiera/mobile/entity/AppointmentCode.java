package com.healthiera.mobile.entity;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Davit on 2/17/2017.
 */
@Table(name = "appointment_code")
public class AppointmentCode  extends Model {

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    public AppointmentCode(String code, String name) {
        super();
        this.code = code;
        this.name = name;
    }

    public AppointmentCode() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
