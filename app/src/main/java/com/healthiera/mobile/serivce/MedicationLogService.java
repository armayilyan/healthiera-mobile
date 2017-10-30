package com.healthiera.mobile.serivce;

import com.healthiera.mobile.entity.MedicationLog;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Davit Ter-Arakelyan on 2/3/2017.
 */

public class MedicationLogService {
    public Long createMeasurementLog(MedicationLog medicationLog) {
        assertThat(medicationLog).isNotNull();
        assertThat(medicationLog.getId()).isNull();
        assertThat(medicationLog.getMedication()).isNotNull();
        medicationLog.setLogDateTime(new Date());
        final Long id = medicationLog.save();
        assertThat(id).isNotNull().isGreaterThan(0L);

        return id;
    }
}
