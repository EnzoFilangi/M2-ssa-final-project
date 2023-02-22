package com.ssa.team3.backend.model.persistence.company;

import com.ssa.team3.backend.model.domain.company.Company;
import com.ssa.team3.backend.model.domain.company.CompanyRepository;
import com.ssa.team3.backend.model.persistence.HibernateUtil;
import jakarta.enterprise.context.ApplicationScoped;
import org.hibernate.Session;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class HibernateBasedCompanyRepository implements CompanyRepository {
    @Override
    public Optional<Company> getCompanyRelatedToTutor(UUID tutorId, UUID id) {
        Session session = HibernateUtil.beginTransaction();

        CompanyEntity companyEntity = session.get(CompanyEntity.class, id);
        try {
            // If the company already has an internship, only return it is the current user owns it
            if (companyEntity.getInternship() != null && !companyEntity.getInternship().getStudent().getTutor().getId().equals(tutorId)) {
                HibernateUtil.endTransaction(session);
                return Optional.empty();
            }
        } catch (NullPointerException e){
            HibernateUtil.endTransaction(session);
            return Optional.empty();
        }

        HibernateUtil.endTransaction(session);
        return Optional.of(companyEntity).map(CompanyEntity::toModel);
    }

    @Override
    public Optional<Company> insertCompany(String name, String address) {
        Session session = HibernateUtil.beginTransaction();

        CompanyEntity companyEntity = new CompanyEntity(name, address);
        session.persist(companyEntity);

        HibernateUtil.endTransaction(session);

        return Optional.of(companyEntity).map(CompanyEntity::toModel);
    }

    @Override
    public boolean updateCompany(UUID tutorId, UUID id, String name, String address) {
        Session session = HibernateUtil.beginTransaction();

        CompanyEntity companyEntity = session.get(CompanyEntity.class, id);
        try {
            // If the company already has an internship, only return it is the current user owns it
            if (companyEntity.getInternship() != null && !companyEntity.getInternship().getStudent().getTutor().getId().equals(tutorId)) {
                HibernateUtil.endTransaction(session);
                return false;
            }
        } catch (NullPointerException e){
            HibernateUtil.endTransaction(session);
            return false;
        }

        session.evict(companyEntity);
        companyEntity.setName(name);
        companyEntity.setAddress(address);
        session.merge(companyEntity);

        HibernateUtil.endTransaction(session);
        return true;
    }
}
