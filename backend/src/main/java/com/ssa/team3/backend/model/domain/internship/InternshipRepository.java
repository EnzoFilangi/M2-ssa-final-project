package com.ssa.team3.backend.model.domain.internship;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public interface InternshipRepository {
    Optional<Internship> getInternshipByTutorById(UUID tutorId, UUID id);

    Optional<Internship> insertInternshipByTutor(UUID tutorId, UUID studentId, UUID companyId, Date startDate, Date endDate, Boolean cahierDesCharges, Boolean ficheVisite, Boolean ficheEvaluationEntreprise, Boolean sondageWeb, Boolean rapportRendu, Boolean sondageWeb1, Boolean visitePlanifiee, Boolean visiteFaite, Float noteTech, Float noteCom);

    boolean updateStudentByTutor(UUID tutorId, UUID id, Date startDate, Date endDate, Boolean cahierDesCharges, Boolean ficheVisite, Boolean ficheEvaluationEntreprise, Boolean sondageWeb, Boolean rapportRendu, Boolean soutenance, Boolean visitePlanifiee, Boolean visiteFaite, Float noteTech, Float noteCom);

    boolean deleteInternshipByTutor(UUID tutorId, UUID id);
}
