package com.healthiera.mobile.serivce;

import com.activeandroid.query.Select;
import com.healthiera.mobile.entity.Medication;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Yengibar Manasyan
 * @date 10/18/16
 */
public class MedicationService {

    public Long createMedication(Medication medication) {
        assertThat(medication).isNotNull();
        assertThat(medication.getId()).isNull();
        assertThat(medication.getSchedule()).isNotNull();
        assertThat(medication.getManufacturer()).isNotNull().isNotEmpty();
        assertThat(medication.getName()).isNotNull().isNotEmpty();
        final Long id = medication.save();
        assertThat(id).isNotNull().isGreaterThan(0L);

        return id;
    }

    public List<Medication> findMedicationByName(String namePattern) {
        List<Medication> medications = new Select()
                .from(Medication.class)
                .where("name LIKE ?",  new String[]{'%' + namePattern + '%'} )
                .execute();
        assertThat(medications).isNotNull();
        return medications;
    }

    public List<Medication> findAllMedications() {
        List<Medication> medications = new Select()
                .from(Medication.class)
                .execute();
        assertThat(medications).isNotNull();
        return medications;
    }



    public Medication findMedicationById(Long id) {
        assertThat(id).isNotNull().isGreaterThan(0L);
        final Medication medication = Medication.load(Medication.class, id);
        assertThat(medication).isNotNull();

        return medication;
    }

    public Medication findMedicationByScheduleId(Long id) {
        assertThat(id).isNotNull().isGreaterThan(0L);
        final Medication medication = new Select().from(Medication.class).where("schedule_id = ?", id).executeSingle();
        assertThat(medication).isNotNull();

        return medication;
    }
}
