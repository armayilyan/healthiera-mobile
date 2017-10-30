package com.healthiera.mobile.entity;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Davit on 2/17/2017.
 */

@Table(name = "medication_list")
public class MedicationList extends Model {
    @Column(name = "name", notNull = true)
    private String name;

    @Column(name = "manufacturer", notNull = true)
    private String manufacturer;

    @Column(name = "dose", notNull = true)
    private String dose;

    public MedicationList() {
        super();
    }

    public MedicationList(String name, String dose, String manufacturer) {
        super();
        this.name = name;
        this.dose = dose;
        this.manufacturer = manufacturer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
}
