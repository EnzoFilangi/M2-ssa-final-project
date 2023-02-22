package com.ssa.team3.backend.controller.http.internship.dto.request;

import com.ssa.team3.backend.controller.annotations.UUID.IsUUID;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

public class CreateInternshipRequest {

    @IsUUID
    private UUID studentId;

    @IsUUID
    private UUID companyId;

    // Use strings for date since JEE doesn't seem to like parsing ISO 8601 dates from the JSON
    @NotBlank
    private String startDate;

    @NotBlank
    private String endDate;

    // We use french terms here as these are business terms
    @NotNull
    private Boolean cahierDesCharges;

    @NotNull
    private Boolean ficheVisite;

    @NotNull
    private Boolean ficheEvaluationEntreprise;

    @NotNull
    private Boolean sondageWeb;

    @NotNull
    private Boolean rapportRendu;

    @NotNull
    private Boolean soutenance;

    @NotNull
    private Boolean visitePlanifiee;

    @NotNull
    private Boolean visiteFaite;

    @NotNull
    private Float noteTech;

    @NotNull
    private Float noteCom;

    public CreateInternshipRequest() {
    }

    public UUID getStudentId() {
        return studentId;
    }

    public UUID getCompanyId() {
        return companyId;
    }

    public Date getStartDate() {
        // Convert the ISO 8601 string to a java Date so the usage is transparent for the rest of the app
        return Date.from(LocalDate.parse(startDate, ISO_LOCAL_DATE).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public Date getEndDate() {
        // Convert the ISO 8601 string to a java Date so the usage is transparent for the rest of the app
        return Date.from(LocalDate.parse(endDate, ISO_LOCAL_DATE).atStartOfDay(ZoneId.systemDefault()).toInstant());
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

    public void setStudentId(UUID studentId) {
        this.studentId = studentId;
    }

    public void setCompanyId(UUID companyId) {
        this.companyId = companyId;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setCahierDesCharges(Boolean cahierDesCharges) {
        this.cahierDesCharges = cahierDesCharges;
    }

    public void setFicheVisite(Boolean ficheVisite) {
        this.ficheVisite = ficheVisite;
    }

    public void setFicheEvaluationEntreprise(Boolean ficheEvaluationEntreprise) {
        this.ficheEvaluationEntreprise = ficheEvaluationEntreprise;
    }

    public void setSondageWeb(Boolean sondageWeb) {
        this.sondageWeb = sondageWeb;
    }

    public void setRapportRendu(Boolean rapportRendu) {
        this.rapportRendu = rapportRendu;
    }

    public void setSoutenance(Boolean soutenance) {
        this.soutenance = soutenance;
    }

    public void setVisitePlanifiee(Boolean visitePlanifiee) {
        this.visitePlanifiee = visitePlanifiee;
    }

    public void setVisiteFaite(Boolean visiteFaite) {
        this.visiteFaite = visiteFaite;
    }

    public void setNoteTech(Float noteTech) {
        this.noteTech = noteTech;
    }

    public void setNoteCom(Float noteCom) {
        this.noteCom = noteCom;
    }
}
