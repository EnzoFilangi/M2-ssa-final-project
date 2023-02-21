package com.ssa.team3.backend.model.domain.internship;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public interface InternshipService {
    Optional<Internship> getInternship(UUID tutorId, UUID id);

    Optional<Internship> addInternship(UUID tutorId, String studentId, String companyId, Date startDate, Date endDate, Boolean cahierDesCharges, Boolean ficheVisite, Boolean ficheEvaluationEntreprise, Boolean sondageWeb, Boolean rapportRendu, Boolean soutenance, Boolean visitePlanifiee, Boolean visiteFaite, Float noteTech, Float noteCom);

    boolean updateInternship(UUID tutorId, UUID id, Date startDate, Date endDate, Boolean cahierDesCharges, Boolean ficheVisite, Boolean ficheEvaluationEntreprise, Boolean sondageWeb, Boolean rapportRendu, Boolean soutenance, Boolean visitePlanifiee, Boolean visiteFaite, Float noteTech, Float noteCom);

    boolean deleteInternship(UUID tutorId, UUID id);
}
