package com.ssa.team3.backend.model.entities;

import com.ssa.team3.backend.model.models.Internship;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;
import java.util.UUID;

@Entity
public class InternshipEntity {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id", nullable = false)
    private StudentEntity student;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id", nullable = false)
    private CompanyEntity company;

    @Temporal(TemporalType.DATE)
    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "end_date", nullable = false)
    private Date endDate;

    private Boolean cahierDesCharges;
    private Boolean ficheVisite;
    private Boolean ficheEvaluationEntreprise;
    private Boolean sondageWeb;
    private Boolean rapportRendu;
    private Boolean soutenance;
    private Boolean visitePlanifiee;
    private Boolean visiteFaite;
    private Float noteTech;
    private Float noteCom;

    public InternshipEntity() {}

    /**
     * Casts this entity to the relevant model
     *
     * Also casts the relations of this entity if fetchType is EAGER, and ignores them if fetchType is LAZY
     * @param fetchType
     * @return
     */
    public Internship toModel(jakarta.persistence.FetchType fetchType){
        switch (fetchType){
            case EAGER:
                return new Internship(id, student.toModel(FetchType.LAZY), company.toModel(FetchType.LAZY), startDate, endDate, cahierDesCharges, ficheVisite, ficheEvaluationEntreprise, sondageWeb, rapportRendu, soutenance, visitePlanifiee, visiteFaite, noteTech, noteCom);
            case LAZY:
            default:
                return new Internship(id, null, null, startDate, endDate, cahierDesCharges, ficheVisite, ficheEvaluationEntreprise, sondageWeb, rapportRendu, soutenance, visitePlanifiee, visiteFaite, noteTech, noteCom);
        }
    }

    /**
     * Casts this entity to the relevant model without casting its relations
     * @return
     */
    public Internship toModel(){
        return toModel(FetchType.LAZY);
    }
}
