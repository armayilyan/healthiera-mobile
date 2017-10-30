package com.healthiera.mobile.serivce;

import com.activeandroid.query.Select;
import com.healthiera.mobile.entity.Doctor;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Yengibar Manasyan
 * @date 10/18/16
 */
public class DcotorService {

    public Long createDoctor(Doctor doctor) {
        assertThat(doctor).isNotNull();
        assertThat(doctor.getId()).isNull();
        assertThat(doctor.getSpecification()).isNotNull().isNotEmpty();
        assertThat(doctor.getName()).isNotNull().isNotEmpty();
        final Long id = doctor.save();
        assertThat(id).isNotNull().isGreaterThan(0L);

        return id;
    }

    public Doctor findDoctorByName(String namePattern) {
        assertThat(namePattern).isNotNull().isNotEmpty();
        final Doctor doctor = new Select()
                .from(Doctor.class)
                .where("name=?",namePattern)
                .executeSingle();
        assertThat(doctor).isNotNull();

        return doctor;
    }

    public List<String> getAllDoctorsName() {
        final List<Doctor> doctor = new Select()
                .from(Doctor.class)
                .execute();
        assertThat(doctor).isNotNull();
        final List<String> doctorName =new ArrayList<>();
        for(int i = 0; i < doctor.size(); i++){
            doctorName.add(doctor.get(i).getName());
        }
        return doctorName;
    }
}
