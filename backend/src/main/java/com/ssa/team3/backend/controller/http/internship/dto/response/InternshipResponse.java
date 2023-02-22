package com.ssa.team3.backend.controller.http.internship.dto.response;

import com.ssa.team3.backend.controller.http.company.dto.response.CompanyResponse;
import com.ssa.team3.backend.controller.http.student.dto.response.StudentResponse;
import com.ssa.team3.backend.model.domain.internship.Internship;

import java.util.Date;
import java.util.UUID;

public class InternshipResponse {
    private final UUID id;
    private final StudentResponse student;
    private final CompanyResponse company;
    private final Date startDate;
    private final Date endDate;

    // We use french terms here as these are business terms
    private final Boolean cahierDesCharges;
    private final Boolean ficheVisite;
    private final Boolean ficheEvaluationEntreprise;
    private final Boolean sondageWeb;
    private final Boolean rapportRendu;
    private final Boolean soutenance;
    private final Boolean visitePlanifiee;
    private final Boolean visiteFaite;
    private final Float noteTech;
    private final Float noteCom;

    public InternshipResponse(Internship internship){
        this.id = internship.getId();
        // Check that the fields are present before trying to serialize them
        this.student = internship.getStudent() == null ? null : new StudentResponse(internship.getStudent());
        this.company = internship.getCompany() == null ? null : new CompanyResponse(internship.getCompany());
        this.startDate = internship.getStartDate();
        this.endDate = internship.getEndDate();
        this.cahierDesCharges = internship.getCahierDesCharges();
        this.ficheVisite = internship.getFicheVisite();
        this.ficheEvaluationEntreprise = internship.getFicheEvaluationEntreprise();
        this.sondageWeb = internship.getSondageWeb();
        this.rapportRendu = internship.getRapportRendu();
        this.soutenance = internship.getSoutenance();
        this.visitePlanifiee = internship.getVisitePlanifiee();
        this.visiteFaite = internship.getVisiteFaite();
        this.noteTech = internship.getNoteTech();
        this.noteCom = internship.getNoteCom();
    }

    public UUID getId() {
        return id;
    }

    public StudentResponse getStudent() {
        return student;
    }

    public CompanyResponse getCompany() {
        return company;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Boolean getCahierDesCharges() {
        return cahierDesCharges;
    }

    public Boolean getFicheVisite() {
        return ficheVisite;
    }

    public Boolean getFicheEvaluationEntreprise() {
        return ficheEvaluationEntreprise;
    }

    public Boolean getSondageWeb() {
        return sondageWeb;
    }

    public Boolean getRapportRendu() {
        return rapportRendu;
    }

    public Boolean getSoutenance() {
        return soutenance;
    }

    public Boolean getVisitePlanifiee() {
        return visitePlanifiee;
    }

    public Boolean getVisiteFaite() {
        return visiteFaite;
    }

    public Float getNoteTech() {
        return noteTech;
    }

    public Float getNoteCom() {
        return noteCom;
    }
}
