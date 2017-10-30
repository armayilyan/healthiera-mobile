package com.healthiera.mobile.entity.model;

/**
 * Created by Davit on 01.10.2016.
 *
 * @author Davit Ter-Arakelyan
 * @date 01.10.2016
 */

public class EventItemModel {

    private int eventType;

    private String eventName;

    private int todayEventCount;

    private int tomorrowEventCount;

    private int image;

    public EventItemModel(int eventType, String eventName, int image) {
        this.eventType = eventType;
        this.eventName = eventName;
        this.image = image;
    }

    public int getImage() {
        return image;
    }

    public int getEventType() {
        return eventType;
    }

    public String getEventName() {
        return eventName;
    }

    public int getTodayEventCount() {
//        int d = 0;
//        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
//        Date date = new Date();
//        List<CalendarFragment> events = new Select()
//                .from(CalendarFragment.class)
//                .where("type = ?", eventName)
//                .execute();
//        for (CalendarFragment e : events) {
//            if (dateFormat.format(e.getStartDateTime()).equals(dateFormat.format(date))) {
//                d++;
//            }
//        }
//        todayEventCount = (new Select()
//                .from(CalendarFragment.class)
//                .where("type = ?", eventName)
//                .where("start_date_time >= ?", date)
//                .execute()).size();
//
//        return todayEventCount = d;
        // TODO add calculation
        return -1;
    }

    public int getTomorrowEventCount() {
//        int d = 0;
//        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
//        Date date = new Date((new Date()).getTime() + (1000 * 60 * 60 * 24));
//        List<CalendarFragment> events = new Select()
//                .from(CalendarFragment.class)
//                .where("type = ?", eventName)
//                .execute();
//        for (CalendarFragment e : events) {
//            if (dateFormat.format(e.getStartDateTime()).equals(dateFormat.format(date))) {
//                d++;
//            }
//        }
//        return tomorrowEventCount = d;
        // TODO add calculation
        return -1;
    }
}
