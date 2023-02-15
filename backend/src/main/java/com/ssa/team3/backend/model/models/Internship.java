package com.ssa.team3.backend.model.models;

import java.util.Date;
import java.util.UUID;

public class Internship {
    private final UUID id;
    private final Student student;
    private final Company company;
    private final Date startDate;
    private final Date endDate;
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

    public Internship(UUID id, Student student, Company company, Date startDate, Date endDate, Boolean cahierDesCharges, Boolean ficheVisite, Boolean ficheEvaluationEntreprise, Boolean sondageWeb, Boolean rapportRendu, Boolean soutenance, Boolean visitePlanifiee, Boolean visiteFaite, Float noteTech, Float noteCom) {
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

    public Student getStudent() {
        return student;
    }

    public Company getCompany() {
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
