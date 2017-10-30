package com.healthiera.mobile.serivce;

import android.util.Log;

import com.activeandroid.query.Select;
import com.healthiera.mobile.entity.AppointmentCode;
import com.healthiera.mobile.entity.Goal;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Melin on 2/18/2017.
 */

public class AppointmentCodeService {
    public Long createAppointmentCode(AppointmentCode appointmentCode) {
        assertThat(appointmentCode).isNotNull();
        assertThat(appointmentCode.getCode()).isNotNull().isNotEmpty();
        assertThat(appointmentCode.getName()).isNotNull().isNotEmpty();
        final Long id = appointmentCode.save();
        Log.e("id",id+"");
        assertThat(id).isNotNull().isGreaterThan(0L);

        return id;
    }

    public List<AppointmentCode> getAllAppointmentCodes() {
        final List<AppointmentCode> appointmentCodes = new Select()
                .from(AppointmentCode.class)
                .execute();
        assertThat(appointmentCodes).isNotNull();

        return appointmentCodes;
    }

    public AppointmentCode getAppointmentCodeByName(String name) {
        final AppointmentCode appointmentCodes = new Select()
                .from(AppointmentCode.class)
                .where("name=", name)
                .executeSingle();
        assertThat(appointmentCodes).isNotNull();

        return appointmentCodes;
    }

    public List<AppointmentCode> getAppointmentNamesByName(String name) {
        List<AppointmentCode> appointmentCodes = new Select()
                .from(AppointmentCode.class)
                .where("name LIKE ?",  new String[]{'%' + name + '%'} )
                .execute();
        assertThat(appointmentCodes).isNotNull();
        return appointmentCodes;
    }

    public List<String> getAppointmentCodesByName(String name) {
        List<AppointmentCode> appointmentCodes = new Select()
                .from(AppointmentCode.class)
                .where("name = ?",  new String[]{name} )
                .execute();
        assertThat(appointmentCodes).isNotNull();

        List<String> appointmentCode =new ArrayList<>();
        for(int i = 0; i < appointmentCodes.size(); i++){
            appointmentCode.add(appointmentCodes.get(i).getCode());
        }

        return appointmentCode;
    }



    public List<String> getAllAppointmentCodesName() {
        final List<AppointmentCode> appointmentCodes = new Select()
                .from(AppointmentCode.class)
                .execute();
        assertThat(appointmentCodes).isNotNull();
        final List<String> appointmentCodesName =new ArrayList<>();
        for(int i = 0; i < appointmentCodes.size(); i++){
            appointmentCodesName.add(appointmentCodes.get(i).getCode());
        }

        return appointmentCodesName;
    }

}
