package com.healthiera.mobile.entity.model;

/**
 * @author Davit Ter-Arakelyan
 * @date 13.10.2016
 */

public class EventViewModel {
    private String name;
    private String value;

    public EventViewModel(String name, String value) {
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
