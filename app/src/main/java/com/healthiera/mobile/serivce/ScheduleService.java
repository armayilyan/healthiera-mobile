package com.healthiera.mobile.serivce;

import com.activeandroid.query.Select;
import com.healthiera.mobile.entity.Schedule;
import com.healthiera.mobile.entity.ScheduleTime;
import com.healthiera.mobile.entity.enumeration.DurationType;
import com.healthiera.mobile.entity.enumeration.RepeatType;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author Yengibar Manasyan
 * @date 10/16/16
 */

public class ScheduleService {

    public Long createSchedule(Schedule schedule) {
        final Date yesterday = new Date(System.currentTimeMillis()-24*60*60*1000);
        assertThat(schedule).isNotNull();
        assertThat(schedule.getId()).isNull();
        assertThat(schedule.getStartDate()).isNotNull().isAfter(yesterday);
        assertThat(schedule.getDurationType()).isNotNull();
        if (schedule.getDurationType().equals(DurationType.NUMBER_OF_DAYS)) {
            assertThat(schedule.getNumberOfRepetition()).isNotNull();
        }
        assertThat(schedule.getRepeatType()).isNotNull();
        if (schedule.getRepeatType().equals(RepeatType.SPECIFIC_DAYS)) {
            assertThat(schedule.getDaysOfWeek()).isNotNull().isNotEmpty();
        } else if (schedule.getRepeatType().equals(RepeatType.DAYS_INTERVAL)) {
            assertThat(schedule.getDaysInterval()).isNotNull().isGreaterThan(0);
        }
        assertThat(schedule.getRemindBeforeMinutes()).isNotNull();
        final Long id = schedule.save();
        assertThat(id).isNotNull().isGreaterThan(0L);

        return id;
    }

    public List<Schedule> getAllSchedules() {
        final List<Schedule> schedules = new Select()
                .from(Schedule.class)
                .execute();
        assertThat(schedules).isNotNull();

        return schedules;
    }

    public List<Schedule> getSchedulesByName(String eventType) {
        final List<Schedule> schedules = new Select()
                .from(Schedule.class)
                .where("event_type = ?", eventType)
                .execute();
        assertThat(schedules).isNotNull();

        return schedules;
    }


    public List<Schedule> getTomorrowSchedules() {
        return null;
    }

    public Integer getTomorrowSchedulesCountByEventType(String eventType) {
                int d = 0;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date((new Date()).getTime() + (1000 * 60 * 60 * 24));
        List<Schedule> events = new Select()
                .from(Schedule.class)
                .where("event_type = ?", eventType)
                .execute();
        for (Schedule e : events) {
            if (dateFormat.format(e.getStartDate()).equals(dateFormat.format(date))) {
                d++;
            }
        }
        return d;
    }

    public Integer getToDaySchedulesCountByEventType(String eventType) {

        int d = 0;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        List<Schedule> events = new Select()
                .from(Schedule.class)
                .where("event_type = ?", eventType)
                .execute();
        for (Schedule e : events) {
            if (dateFormat.format(e.getStartDate()).equals(dateFormat.format(date))) {
                d++;
            }
        }
//        todayEventCount = (new Select()
//                .from(CalendarFragment.class)
//                .where("type = ?", eventName)
//                .where("start_date_time >= ?", date)
//                .execute()).size();

        return d;
    }

    public Schedule getScheduleById(Long id) {
        return new Select()
                .from(Schedule.class)
                .where("id = ?", id)
                .executeSingle();
    }

    public Schedule getScheduleByScheduleTimeId(Long id)
    {
        ScheduleTime scheduleTime = new Select()
                .from(ScheduleTime.class)
                .where("id = ?", id)
                .executeSingle();
        return new Select()
                .from(Schedule.class)
                .where("id = ?", scheduleTime.getSchedule().getId())
                .executeSingle();
    }
}
