package com.healthiera.mobile.serivce;

import com.activeandroid.query.Select;
import com.healthiera.mobile.entity.Goal;
import com.healthiera.mobile.entity.Measurement;
import com.healthiera.mobile.entity.MeasurementLog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static android.R.attr.id;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Yengibar Manasyan
 * @date 10/18/16
 */
public class MeasurementLogService {

    public Long createMeasurementLog(MeasurementLog measurementLog) {
        assertThat(measurementLog).isNotNull();
        assertThat(measurementLog.getId()).isNull();
        assertThat(measurementLog.getMeasurement()).isNotNull();
        assertThat(measurementLog.getValue()).isNotNull().isNotEmpty();
        measurementLog.setLogDateTime(new Date());
        final Long id = measurementLog.save();
        assertThat(id).isNotNull().isGreaterThan(0L);

        return id;
    }

    public int[] getChartData(String goalName)
    {
        int[] list = new int[30];
        int i=0;
        List<MeasurementLog> logList =new Select().from(MeasurementLog.class).execute();

        for(MeasurementLog itme : logList)
        {
            if(itme.getMeasurement().getGoal().getName().equals(goalName))
            {
                list[i]=Integer.valueOf(itme.getValue());
                i++;
                if(i==29) break;
            }
        }
        return list;
    }


    public List<MeasurementLog> GetLastLogedMeasurement()
    {
        List<MeasurementLog> list=new ArrayList<MeasurementLog>();
        List<MeasurementLog> logList =new Select().from(MeasurementLog.class).execute();
        List<Goal> goalList = new Select().from(Goal.class).execute();

        for (Goal item : goalList) {
            List<Measurement> measurements = new Select().from(Measurement.class).where("goal_id = ?", item.getId()).execute();
            List<MeasurementLog> tmpList=new ArrayList<MeasurementLog>();
            for (MeasurementLog log : logList)
            {
                for (Measurement measurement : measurements)
                if(log.getMeasurement()==measurement)
                {
                    tmpList.add(log);
                }
            }

            Collections.sort(tmpList, new Comparator<MeasurementLog>() {
                @Override
                public int compare(MeasurementLog c1, MeasurementLog c2) {
                    return c2.getLogDateTime().compareTo(c1.getLogDateTime());
                }
            });
            if(tmpList.size()>0)
            list.add(tmpList.get(0));
                    else
            {
                MeasurementLog t=new MeasurementLog();
                t.setValue("Not Set");
                list.add(t);
            }
        }
        return list;
    }
}
