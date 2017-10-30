package com.healthiera.mobile.serivce;

import android.util.Log;

import com.activeandroid.query.Select;
import com.healthiera.mobile.entity.AppointmentCode;
import com.healthiera.mobile.entity.TreatmentCode;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Melin on 2/18/2017.
 */

public class TreatmentCodeService {
    public Long createTreatmentCode(TreatmentCode treatmentCode) {
        assertThat(treatmentCode).isNotNull();
        assertThat(treatmentCode.getCode()).isNotNull().isNotEmpty();
        assertThat(treatmentCode.getName()).isNotNull().isNotEmpty();
        final Long id = treatmentCode.save();
        Log.e("id",id+"");
        assertThat(id).isNotNull().isGreaterThan(0L);

        return id;
    }

    public List<TreatmentCode> getAllTreatmentCodes() {
        final List<TreatmentCode> treatmentCodes = new Select()
                .from(TreatmentCode.class)
                .execute();
        assertThat(treatmentCodes).isNotNull();

        return treatmentCodes;
    }

    public TreatmentCode getTreatmentCodeByName(String name) {
        final TreatmentCode treatmentCodes = new Select()
                .from(TreatmentCode.class)
                .where("name=", name)
                .executeSingle();
        assertThat(treatmentCodes).isNotNull();

        return treatmentCodes;
    }

    public List<TreatmentCode> getTreatmentNamesByName(String name) {
        List<TreatmentCode> treatmentCodes = new Select()
                .from(TreatmentCode.class)
                .where("name LIKE ?",  new String[]{'%' + name + '%'} )
                .execute();
        assertThat(treatmentCodes).isNotNull();
        return treatmentCodes;
    }

    public List<String> getTreatmentCodesByName(String name) {
        List<TreatmentCode> treatmentCodes = new Select()
                .from(TreatmentCode.class)
                .where("name = ?",  new String[]{name} )
                .execute();
        assertThat(treatmentCodes).isNotNull();

        List<String> treatmentCode =new ArrayList<>();
        for(int i = 0; i < treatmentCodes.size(); i++){
            treatmentCode.add(treatmentCodes.get(i).getCode());
        }

        return treatmentCode;
    }



    public List<String> getAllTreatmentCodesName() {
        final List<TreatmentCode> treatmentCodes = new Select()
                .from(TreatmentCode.class)
                .execute();
        assertThat(treatmentCodes).isNotNull();
        final List<String> treatmentCodesName =new ArrayList<>();
        for(int i = 0; i < treatmentCodesName.size(); i++){
            treatmentCodesName.add(treatmentCodes.get(i).getCode());
        }

        return treatmentCodesName;
    }

}
