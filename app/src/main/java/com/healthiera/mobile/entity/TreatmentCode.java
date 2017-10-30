package com.healthiera.mobile.entity;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Davit on 2/17/2017.
 */

@Table(name = "treatment_code")
public class TreatmentCode extends Model {

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    public TreatmentCode(String code) {
        super();
        this.code = code;
    }

    public TreatmentCode() {
        super();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
