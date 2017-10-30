package com.healthiera.mobile.serivce;

import android.util.Log;

import com.activeandroid.query.Select;
import com.healthiera.mobile.entity.Schedule;
import com.healthiera.mobile.entity.ScheduleTime;
import com.healthiera.mobile.entity.ScheduleTimeLog;
import com.healthiera.mobile.entity.enumeration.DurationType;
import com.healthiera.mobile.entity.enumeration.EventType;
import com.healthiera.mobile.entity.enumeration.RepeatType;
import com.healthiera.mobile.entity.enumeration.ScheduleStatus;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Yengibar Manasyan
 * @date 10/18/16
 */
public class ScheduleTimeService {

    private static final int WEEK_DAYS_COUNT = 7;

    private static final ScheduleService scheduleService = new ScheduleService();

    private static final ScheduleTimeLogService SCHEDULE_TIME_LOG_SERVICE = new ScheduleTimeLogService();

    public Long createSchedule(ScheduleTime scheduleTime) {
        assertThat(scheduleTime).isNotNull();
        assertThat(scheduleTime.getId());//.isNull();
        assertThat(scheduleTime.getSchedule()).isNotNull();
        assertThat(scheduleTime.getTime()).isNotNull();
        final Long id = scheduleTime.save();
        assertThat(id).isNotNull().isGreaterThan(0L);

        return id;
    }

    public List<ScheduleTime> getByScheduleId(Long scheduleId) {
        assertThat(scheduleId).isNotNull();
        final List<ScheduleTime> scheduleTimes = new Select()
                .from(ScheduleTime.class)
                .where("schedule_id = ?", scheduleId)
                .execute();
        assertThat(scheduleTimes).isNotNull();

        return scheduleTimes;
    }

    public List<ScheduleTime> getDateNotDoneScheduleTimes(LocalDate currentDate, String EventType) {
        final List<Schedule> schedules = scheduleService.getSchedulesByName(EventType);
        return getDateNotDoneScheduleTimesBySchedules(currentDate, schedules);
    }

    public List<ScheduleTime> getDateNotDoneScheduleTimesAll(LocalDate currentDate) {
        final List<Schedule> schedules = scheduleService.getAllSchedules();
        return getDateNotDoneScheduleTimesBySchedules(currentDate, schedules);
    }

    public List<ScheduleTime> getDateNotDoneScheduleTimesBySchedules(LocalDate currentDate, List<Schedule> schedules) {
        final List<ScheduleTime> todayScheduleTimes = new ArrayList<>();
        LocalDate date;

        Log.e("currentdate", currentDate.toString());
        for (Schedule schedule : schedules) {
            final LocalDate startDate = new LocalDate(schedule.getStartDate());
            if (!startDate.isAfter(currentDate)) {
                if (schedule.getDurationType().equals(DurationType.NUMBER_OF_DAYS)) {
                    if (!startDate.isAfter(currentDate) &&
                            startDate.plusDays(schedule.getNumberOfRepetition()).isAfter(currentDate)) {
                        if (schedule.getRepeatType().equals(RepeatType.EVERY_DAY)) {
                            todayScheduleTimes.addAll(getByScheduleId(schedule.getId()));
                        } else if (schedule.getRepeatType().equals(RepeatType.DAYS_INTERVAL)) {
                            date = startDate;
                            while (!date.isAfter(currentDate)) {
                                if (date.equals(currentDate)) {
                                    todayScheduleTimes.addAll(getByScheduleId(schedule.getId()));
                                    break;
                                }
                                date = date.plusDays(schedule.getDaysInterval());
                            }

                        } else if (schedule.getRepeatType().equals(RepeatType.SPECIFIC_DAYS)) {
                            String[] weekDays = schedule.getDaysOfWeek().split(",");
                            for (int i = 0; i < weekDays.length; i++) {
                                if (currentDate.getDayOfWeek() == getWeekDaysNumber(weekDays[i])) {
                                    todayScheduleTimes.addAll(getByScheduleId(schedule.getId()));
                                }
                                ;
                            }
                        }
                    }
                } else if (schedule.getDurationType().equals(DurationType.CONTINOUS)) {
                    if (schedule.getRepeatType().equals(RepeatType.EVERY_DAY)) {
                        todayScheduleTimes.addAll(getByScheduleId(schedule.getId()));
                    } else if (schedule.getRepeatType().equals(RepeatType.DAYS_INTERVAL)) {
                        date = startDate;
                        while (!date.isAfter(currentDate)) {
                            if (date.equals(currentDate)) {
                                todayScheduleTimes.addAll(getByScheduleId(schedule.getId()));
                                break;
                            }
                            date = date.plusDays(schedule.getDaysInterval());
                        }

                    } else if (schedule.getRepeatType().equals(RepeatType.SPECIFIC_DAYS)) {
                        String[] weekDays = schedule.getDaysOfWeek().split(",");
                        for (int i = 0; i < weekDays.length; i++) {
                            if (currentDate.getDayOfWeek() == getWeekDaysNumber(weekDays[i])) {
                                todayScheduleTimes.addAll(getByScheduleId(schedule.getId()));
                            }
                            ;
                        }
                    }
                }
            }
        }
        return todayScheduleTimes;
    }

    private int getWeekDaysNumber(String weekDay) {
        switch (weekDay.replaceAll(" ", "")) {
            case "Mon":
                return 1;
            case "Tue":
                return 2;
            case "Wed":
                return 3;
            case "Thu":
                return 4;
            case "Fri":
                return 5;
            case "Sat":
                return 6;
            case "Sun":
                return 7;
            default:
                return -1;
        }
    }

    public List<ScheduleTime> getNearService(LocalDate currentDate) {
        List<ScheduleTime> scheduleTimes = getDateNotDoneScheduleTimesAll(currentDate);
        List<ScheduleTime> scheduleTimes1 = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int hours = calendar.get(Calendar.MINUTE)+ calendar.get(Calendar.HOUR_OF_DAY)*60;

        for (ScheduleTime scheduleTime : scheduleTimes) {
            if (scheduleTime.getTime() > hours && scheduleTime.getTime() - hours <15){
                scheduleTimes1.add(scheduleTime);

            }
        }

        return scheduleTimes1;
    }

    public List<ScheduleTime> getDateNotDoneScheduleTimes1(LocalDate date, String EventType) {
        assertThat(date).isNotNull();
        final List<Schedule> schedules = scheduleService.getSchedulesByName(EventType);
        final List<ScheduleTime> todayScheduleTimes = new ArrayList<>();
        for (Schedule schedule : schedules) {
            final LocalDate startDate = new LocalDate(schedule.getStartDate());
            if (schedule.getDurationType().equals(DurationType.NUMBER_OF_DAYS)) {
                final int daysToAdd;
                if (schedule.getRepeatType().equals(RepeatType.EVERY_DAY)) {
                    daysToAdd = schedule.getNumberOfRepetition();
                } else if (schedule.getRepeatType().equals(RepeatType.DAYS_INTERVAL)) {
                    daysToAdd = schedule.getNumberOfRepetition() * schedule.getDaysInterval();
                } else {
                    daysToAdd = schedule.getNumberOfRepetition() * WEEK_DAYS_COUNT;
                }
                if (startDate.plusDays(daysToAdd).isAfter(date)) {
                    continue;
                }
            }

            boolean checkTime = false;
            if (schedule.getRepeatType().equals(RepeatType.EVERY_DAY)) {
                checkTime = true;
            } else if (schedule.getRepeatType().equals(RepeatType.DAYS_INTERVAL)) {
                if (schedule.getNumberOfRepetition() != null)
                    for (int i = 0; i < schedule.getNumberOfRepetition(); i++) {
                        if (startDate.plusDays(i * schedule.getDaysInterval()).isEqual(date)) {
                            checkTime = true;
                        }
                    }
            } else {
                final String[] weekDays = schedule.getDaysOfWeek().split(",");
                if (Arrays.asList(weekDays).contains(String.valueOf(date.getDayOfWeek()))) {
                    checkTime = true;
                }
            }

            if (checkTime) {
                final List<ScheduleTime> scheduleTimes = getByScheduleId(schedule.getId());
                final DateTime currentTime = DateTime.now();
                for (ScheduleTime scheduleTime : scheduleTimes) {
                    if (currentTime.isBefore(date.toDateTimeAtStartOfDay().plusMinutes(scheduleTime.getTime()))) {
                        todayScheduleTimes.add(scheduleTime);
                    } else {
                        final List<ScheduleTimeLog> scheduleTimeLogs = SCHEDULE_TIME_LOG_SERVICE
                                .getByScheduleTimeIdAndDate(scheduleTime.getId(), date);
                        boolean addToList = true;
                        for (ScheduleTimeLog scheduleTimeLog : scheduleTimeLogs) {
                            final ScheduleStatus scheduleStatus = scheduleTimeLog.getScheduleStatus();
                            if (scheduleStatus.equals(ScheduleStatus.DONE) ||
                                    scheduleStatus.equals(ScheduleStatus.CANCELLED) ||
                                    scheduleStatus.equals(ScheduleStatus.DELETED)) {
                                addToList = false;
                            }
                        }
                        if (addToList) {
                            todayScheduleTimes.add(scheduleTime);
                        }
                    }
                }
            }
        }

        return todayScheduleTimes;
    }
}
