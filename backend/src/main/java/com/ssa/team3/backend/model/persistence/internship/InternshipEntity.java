package com.ssa.team3.backend.model.persistence.internship;

import com.ssa.team3.backend.model.persistence.company.CompanyEntity;
import com.ssa.team3.backend.model.persistence.student.StudentEntity;
import com.ssa.team3.backend.model.domain.internship.Internship;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "INTERNSHIPS")
public class InternshipEntity {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id", nullable = false)
    private StudentEntity student;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id", nullable = false)
    private CompanyEntity company;

    @Temporal(TemporalType.DATE)
    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "end_date", nullable = false)
    private Date endDate;

    // We use french terms here as these are business terms
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

    public InternshipEntity(StudentEntity student, CompanyEntity company, Date startDate, Date endDate, Boolean cahierDesCharges, Boolean ficheVisite, Boolean ficheEvaluationEntreprise, Boolean sondageWeb, Boolean rapportRendu, Boolean soutenance, Boolean visitePlanifiee, Boolean visiteFaite, Float noteTech, Float noteCom) {
        this.student = student;
        this.company = company;
        this.startDate = startDate;
        this.endDate = endDate;
        this.cahierDesCharges = cahierDesCharges;
        this.ficheVisite = ficheVisite;
        this.ficheEvaluationEntreprise = ficheEvaluationEntreprise;
        this.sondageWeb = sondageWeb;
        this.rapportRendu = rapportRendu;
        this.soutenance = soutenance;
        this.visitePlanifiee = visitePlanifiee;
        this.visiteFaite = visiteFaite;
        this.noteTech = noteTech;
        this.noteCom = noteCom;
    }

    public InternshipEntity() {}

    public InternshipEntity(UUID id, StudentEntity student, CompanyEntity company, Date startDate, Date endDate, Boolean cahierDesCharges, Boolean ficheVisite, Boolean ficheEvaluationEntreprise, Boolean sondageWeb, Boolean rapportRendu, Boolean soutenance, Boolean visitePlanifiee, Boolean visiteFaite, Float noteTech, Float noteCom) {
        this.id = id;
        this.student = student;
        this.company = company;
        this.startDate = startDate;
        this.endDate = endDate;
        this.cahierDesCharges = cahierDesCharges;
        this.ficheVisite = ficheVisite;
        this.ficheEvaluationEntreprise = ficheEvaluationEntreprise;
        this.sondageWeb = sondageWeb;
        this.rapportRendu = rapportRendu;
        this.soutenance = soutenance;
        this.visitePlanifiee = visitePlanifiee;
        this.visiteFaite = visiteFaite;
        this.noteTech = noteTech;
        this.noteCom = noteCom;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public StudentEntity getStudent() {
        return student;
    }

    public void setStudent(StudentEntity student) {
        this.student = student;
    }

    public CompanyEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Boolean getCahierDesCharges() {
        return cahierDesCharges;
    }

    public void setCahierDesCharges(Boolean cahierDesCharges) {
        this.cahierDesCharges = cahierDesCharges;
    }

    public Boolean getFicheVisite() {
        return ficheVisite;
    }

    public void setFicheVisite(Boolean ficheVisite) {
        this.ficheVisite = ficheVisite;
    }

    public Boolean getFicheEvaluationEntreprise() {
        return ficheEvaluationEntreprise;
    }

    public void setFicheEvaluationEntreprise(Boolean ficheEvaluationEntreprise) {
        this.ficheEvaluationEntreprise = ficheEvaluationEntreprise;
    }

    public Boolean getSondageWeb() {
        return sondageWeb;
    }

    public void setSondageWeb(Boolean sondageWeb) {
        this.sondageWeb = sondageWeb;
    }

    public Boolean getRapportRendu() {
        return rapportRendu;
    }

    public void setRapportRendu(Boolean rapportRendu) {
        this.rapportRendu = rapportRendu;
    }

    public Boolean getSoutenance() {
        return soutenance;
    }

    public void setSoutenance(Boolean soutenance) {
        this.soutenance = soutenance;
    }

    public Boolean getVisitePlanifiee() {
        return visitePlanifiee;
    }

    public void setVisitePlanifiee(Boolean visitePlanifiee) {
        this.visitePlanifiee = visitePlanifiee;
    }

    public Boolean getVisiteFaite() {
        return visiteFaite;
    }

    public void setVisiteFaite(Boolean visiteFaite) {
        this.visiteFaite = visiteFaite;
    }

    public Float getNoteTech() {
        return noteTech;
    }

    public void setNoteTech(Float noteTech) {
        this.noteTech = noteTech;
    }

    public Float getNoteCom() {
        return noteCom;
    }

    public void setNoteCom(Float noteCom) {
        this.noteCom = noteCom;
    }

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
