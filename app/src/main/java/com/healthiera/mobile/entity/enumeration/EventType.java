package com.healthiera.mobile.entity.enumeration;

import com.healthiera.mobile.R;
import com.healthiera.mobile.entity.model.EventItemModel;

/**
 * Created by Davit on 05.08.2016.
 */
public enum EventType {

    Appointment(new EventItemModel(1, "Appointment", R.drawable.appointment)),
    Medication(new EventItemModel(2, "Medication", R.drawable.medication)),
    Measurement(new EventItemModel(3, "Measurement", R.drawable.messurment)),
    Treatment(new EventItemModel(4, "Treatment", R.drawable.treatment));

    private int event;

    private String eventName;

    private int eventIcon;

    EventType(EventItemModel model) {
        event = model.getEventType();
        eventName = model.getEventName();
        eventIcon = model.getImage();
    }

    public int getEvent() {
        return event;
    }

    public String getEventName() {
        return eventName;
    }

    public int getEventIcon() {
        return eventIcon;
    }
}
