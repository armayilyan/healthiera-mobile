package com.healthiera.mobile.entity;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * @author Davit Ter-Arakelyan
 * @date 21.09.2016
 */
@Table(name = "goal")
public class Goal extends Model {

    @Column(name = "name", notNull = true)
    private String name;

    @Column(name = "value", notNull = true)
    private String value;

    public Goal() {
        super();
    }

    public Goal(String name, String value) {
        super();
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
