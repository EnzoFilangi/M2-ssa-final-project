package com.ssa.team3.backend.model.domain.internship;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class RepositoryBasedInternshipService implements InternshipService {
    @Inject InternshipRepository internshipRepository;

    @Override
    public Optional<Internship> getInternship(UUID tutorId, UUID id) {
        return internshipRepository.getInternshipByTutorById(tutorId, id);
    }

    @Override
    public Optional<Internship> addInternship(UUID tutorId, UUID studentId, UUID companyId, Date startDate, Date endDate, Boolean cahierDesCharges, Boolean ficheVisite, Boolean ficheEvaluationEntreprise, Boolean sondageWeb, Boolean rapportRendu, Boolean soutenance, Boolean visitePlanifiee, Boolean visiteFaite, Float noteTech, Float noteCom) {
        return internshipRepository.insertInternshipByTutor(tutorId, studentId, companyId, startDate, endDate, cahierDesCharges, ficheVisite, ficheEvaluationEntreprise, sondageWeb, rapportRendu, sondageWeb, visitePlanifiee, visiteFaite, noteTech, noteCom);
    }

    @Override
    public boolean updateInternship(UUID tutorId, UUID id, Date startDate, Date endDate, Boolean cahierDesCharges, Boolean ficheVisite, Boolean ficheEvaluationEntreprise, Boolean sondageWeb, Boolean rapportRendu, Boolean soutenance, Boolean visitePlanifiee, Boolean visiteFaite, Float noteTech, Float noteCom) {
        return internshipRepository.updateStudentByTutor(tutorId, id, startDate, endDate, cahierDesCharges, ficheVisite, ficheEvaluationEntreprise, sondageWeb, rapportRendu, soutenance, visitePlanifiee, visiteFaite, noteTech, noteCom);
    }

    @Override
    public boolean deleteInternship(UUID tutorId, UUID id) {
        return internshipRepository.deleteInternshipByTutor(tutorId, id);
    }
}
