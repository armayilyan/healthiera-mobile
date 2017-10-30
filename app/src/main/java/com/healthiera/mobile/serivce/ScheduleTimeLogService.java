package com.healthiera.mobile.serivce;

import com.activeandroid.query.Select;
import com.healthiera.mobile.entity.ScheduleTimeLog;

import org.joda.time.LocalDate;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Yengibar Manasyan
 * @date 10/18/16
 */
public class ScheduleTimeLogService {

    public Long createScheduleTimeLog(ScheduleTimeLog scheduleTimeLog) {
        assertThat(scheduleTimeLog).isNotNull();
        assertThat(scheduleTimeLog.getId()).isNull();
        assertThat(scheduleTimeLog.getScheduleStatus()).isNotNull();
        scheduleTimeLog.setDateTime(new Date());
        final Long id = scheduleTimeLog.save();
        assertThat(id).isNotNull().isGreaterThan(0L);

        return id;
    }

    public List<ScheduleTimeLog> getByScheduleTimeIdAndDate(Long scheduleTimeId, LocalDate date) {
        assertThat(scheduleTimeId).isNotNull();
        final List<ScheduleTimeLog> scheduleTimeLogs = new Select()
                .from(ScheduleTimeLog.class)
                .where("schedule_time_id = ? and date_time between ? and ?",
                        scheduleTimeId, date.toDateTimeAtStartOfDay(), date.plusDays(1).toDateTimeAtStartOfDay().minusMillis(1))
                .execute();
        assertThat(scheduleTimeLogs).isNotNull();

        return scheduleTimeLogs;
    }
}
