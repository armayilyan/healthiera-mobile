package com.healthiera.mobile.serivce;

import com.activeandroid.query.Select;
import com.healthiera.mobile.entity.Medication;
import com.healthiera.mobile.entity.MedicationList;
import com.healthiera.mobile.entity.TreatmentCode;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Yengibar Manasyan
 * @date 10/18/16
 */
public class MedicationListService {

    public void loadAllMedicationList() {
        String[] medicationNames = new String[]{"aspirin", "analgin", "aspirine", "cymbalta", "metformin",
                "acetaminophen", "adderall", "alprazolam", "amitriptyline", "amlodipine",
                "amoxicillin", "ativan", "atorvastatin", "azithromycin", "ciprofloxacin",
                "citalopram", "clindamycin", "clonazepam", "codeine", "cyclobenzaprine",
                "doxycycline", "Gabapentin", "hydrochlorothiazide", "ibuprofen", "lexapro",
                "lisinopril", "loratadine", "lorazepam", "losartan", "lyrica", "meloxicam",
                "metoprolol", "naproxen", "omeprazole", "oxycodone", "pantoprazole",
                "tramadol", "trazodone", "viagra", "wellbutrin", "xanax", "zoloft", "prednisone"};

        String[] manufactures = new String[]{
                "A-S Medication Solutions, LLC", "A&Z Pharmaceutical, Inc.", "AAIPharma",
                "Acura Pharmaceuticals, Inc.", "Acusphere, Inc.", "Bayer HealthCare Pharmaceuticals Inc.",
                "BD Rx Inc.", "BioDelivery Sciences International, Inc.", "Biofrontera AG",
                "Capellon Pharmaceuticals, LLC", "Caraco Pharmaceutical Laboratories, Ltd.", "Cardinal Health",
                "Chain Drug Marketing Association", "Eagle Pharmaceuticals, Inc.", "Genentech, Inc.",
                "Luitpold Pharmaceuticals, Inc.", "Marathon Pharmaceuticals, LLC", "Merck & Co., Inc.",
                "Nagase America Corp.", "Novartis Consumer Health", "Novartis Pharmaceuticals Corporation",
                "Onyx Pharmaceuticals, Inc.", "Otonomy, Inc.", "Pernix Therapeutics",
                "Perrigo Company", "Ranbaxy Pharmaceuticals Inc.", "Reckitt Benckiser Pharmaceuticals Inc.",
                "Revive Personal Products, Inc.",
                "Salix Pharmaceuticals, Inc.", "Sarepta Therapeutics", "Sebela Pharmaceuticals, Inc.", "Sepracor Inc.",
                "Sun Pharmaceutical Industries Inc.", "Sunovion Pharmaceuticals Inc.", "Titan Pharmaceuticals, Inc.",
                "Vertex Pharmaceuticals Incorporated", "Wockhardt USA", "X-GEN Pharmaceuticals, Inc.",
                "Xanodyne Pharmaceuticals, Inc", "XenoPort, Inc", "Zila, Inc.", "Zogenix, Inc."
        };

        for (int i = 0; i < medicationNames.length; i++) {
            CreateMedicationList(new MedicationList(medicationNames[i], manufactures[i], Math.random() * 50 + ""));
        }
    }

    public Long CreateMedicationList(MedicationList medicationList) {
        assertThat(medicationList).isNotNull();
        assertThat(medicationList.getName()).isNotNull().isNotEmpty();
        assertThat(medicationList.getDose()).isNotNull().isNotEmpty();
        assertThat(medicationList.getManufacturer()).isNotNull().isNotEmpty();

        final Long id = medicationList.save();
        assertThat(id).isNotNull().isGreaterThan(0L);

        return id;
    }

    public List<MedicationList> findMedicationByName() {
        final List<MedicationList> medicationList = new Select()
                .from(MedicationList.class)
                .execute();
        assertThat(medicationList).isNotNull();

        return medicationList;
    }


    public MedicationList findMedicationById(Long id) {
        assertThat(id).isNotNull().isGreaterThan(0L);
        final MedicationList medication = MedicationList.load(MedicationList.class, id);
        assertThat(medication).isNotNull();

        return medication;
    }

    public MedicationList findMedicationByScheduleId(Long id) {
        assertThat(id).isNotNull().isGreaterThan(0L);
        final MedicationList medication = new Select().from(MedicationList.class).where("schedule_id = ?", id).executeSingle();
        assertThat(medication).isNotNull();

        return medication;
    }
}
