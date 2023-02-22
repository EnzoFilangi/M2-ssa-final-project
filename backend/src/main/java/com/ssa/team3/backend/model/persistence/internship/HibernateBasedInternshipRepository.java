package com.ssa.team3.backend.model.persistence.internship;

import com.ssa.team3.backend.model.domain.internship.Internship;
import com.ssa.team3.backend.model.domain.internship.InternshipRepository;
import com.ssa.team3.backend.model.persistence.HibernateUtil;
import com.ssa.team3.backend.model.persistence.company.CompanyEntity;
import com.ssa.team3.backend.model.persistence.student.StudentEntity;
import jakarta.enterprise.context.ApplicationScoped;
import org.hibernate.Session;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class HibernateBasedInternshipRepository implements InternshipRepository {
    @Override
    public Optional<Internship> getInternshipByTutorById(UUID tutorId, UUID id) {
        Session session = HibernateUtil.beginTransaction();

        InternshipEntity internshipEntity = session.get(InternshipEntity.class, id);
        if (internshipEntity == null || !internshipEntity.getStudent().getTutor().getId().equals(tutorId)) {
            HibernateUtil.endTransaction(session);
            return Optional.empty();
        }

        HibernateUtil.endTransaction(session);
        return Optional.of(internshipEntity).map(entity -> entity.toModel(true));
    }

    @Override
    public Optional<Internship> insertInternshipByTutor(UUID tutorId, UUID studentId, UUID companyId, Date startDate, Date endDate, Boolean cahierDesCharges, Boolean ficheVisite, Boolean ficheEvaluationEntreprise, Boolean sondageWeb, Boolean rapportRendu, Boolean sondageWeb1, Boolean visitePlanifiee, Boolean visiteFaite, Float noteTech, Float noteCom) {
        Session session = HibernateUtil.beginTransaction();

        StudentEntity student = session.get(StudentEntity.class, studentId);
        if (student == null || !student.getTutor().getId().equals(tutorId)){ // Refuse non existent student, or student that isn't owned by this tutor
            HibernateUtil.endTransaction(session);
            return Optional.empty();
        }
        CompanyEntity company = session.get(CompanyEntity.class, companyId);
        if (company == null || company.getInternship() != null){ // Refuse non existent companies, or companies that are already attributed
            HibernateUtil.endTransaction(session);
            return Optional.empty();
        }
        InternshipEntity internshipEntity = new InternshipEntity(student, company, startDate, endDate, cahierDesCharges, ficheVisite, ficheEvaluationEntreprise, sondageWeb, rapportRendu, sondageWeb, visitePlanifiee, visiteFaite, noteTech, noteCom);
        session.persist(internshipEntity);

        HibernateUtil.endTransaction(session);
        return Optional.of(internshipEntity).map(entity -> entity.toModel(true));
    }

    @Override
    public boolean updateStudentByTutor(UUID tutorId, UUID id, Date startDate, Date endDate, Boolean cahierDesCharges, Boolean ficheVisite, Boolean ficheEvaluationEntreprise, Boolean sondageWeb, Boolean rapportRendu, Boolean soutenance, Boolean visitePlanifiee, Boolean visiteFaite, Float noteTech, Float noteCom) {
        Session session = HibernateUtil.beginTransaction();

        InternshipEntity internshipEntity = session.get(InternshipEntity.class, id);
        if (internshipEntity == null || !internshipEntity.getStudent().getTutor().getId().equals(tutorId)){
            HibernateUtil.endTransaction(session);
            return false;
        }

        session.evict(internshipEntity);
        internshipEntity.setStartDate(startDate);
        internshipEntity.setEndDate(endDate);
        internshipEntity.setCahierDesCharges(cahierDesCharges);
        internshipEntity.setFicheVisite(ficheVisite);
        internshipEntity.setFicheEvaluationEntreprise(ficheEvaluationEntreprise);
        internshipEntity.setSondageWeb(sondageWeb);
        internshipEntity.setRapportRendu(rapportRendu);
        internshipEntity.setSoutenance(soutenance);
        internshipEntity.setVisitePlanifiee(visitePlanifiee);
        internshipEntity.setVisiteFaite(visiteFaite);
        internshipEntity.setNoteTech(noteTech);
        internshipEntity.setNoteCom(noteCom);
        session.merge(internshipEntity);

        HibernateUtil.endTransaction(session);
        return true;
    }

    @Override
    public boolean deleteInternshipByTutor(UUID tutorId, UUID id) {
        Session session = HibernateUtil.beginTransaction();

        InternshipEntity internshipEntity = session.get(InternshipEntity.class, id);
        if (internshipEntity == null || !internshipEntity.getStudent().getTutor().getId().equals(tutorId)){
            HibernateUtil.endTransaction(session);
            return false;
        }

        session.remove(internshipEntity);
        HibernateUtil.endTransaction(session);
        return true;
    }
}
