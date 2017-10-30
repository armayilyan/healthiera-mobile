package com.healthiera.mobile.serivce;

import com.activeandroid.query.Select;
import com.healthiera.mobile.entity.Procedure;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author Yengibar Manasyan
 * @date 8/14/16
 */
public class ProcedureService {

    public Long createProcedure(Procedure procedure) {
        assertThat(procedure).isNotNull();
        assertThat(procedure.getId()).isNull();
        assertThat(procedure.getDoctor()).isNotNull();
        assertThat(procedure.getSchedule()).isNotNull();
        final Long id = procedure.save();
        assertThat(id).isGreaterThan(0L);

        return id;
    }

    public Procedure findProcedureById(Long id) {
        assertThat(id).isNotNull().isGreaterThan(0L);
        final Procedure procedure = Procedure.load(Procedure.class, id);
        assertThat(procedure).isNotNull();

        return procedure;
    }

    public Procedure findProcedureByScheduleId(Long id) {
        assertThat(id).isNotNull().isGreaterThan(0L);
        final Procedure procedure = new Select().from(Procedure.class).where("schedule_id = ?", id).executeSingle();
        assertThat(procedure).isNotNull();

        return procedure;
    }
}
