package com.ssa.team3.backend.model.persistence.company;

import com.ssa.team3.backend.model.domain.company.Company;
import com.ssa.team3.backend.model.domain.company.CompanyRepository;
import com.ssa.team3.backend.model.persistence.HibernateUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.hibernate.Session;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class HibernateBasedCompanyRepository implements CompanyRepository {
    @Inject HibernateUtil hibernate;

    @Override
    public Optional<Company> getCompanyRelatedToTutor(UUID tutorId, UUID id) {
        Session session = hibernate.beginTransaction();

        CompanyEntity companyEntity = session.get(CompanyEntity.class, id);
        try {
            // If the company already has an internship, only return it is the current user owns it
            if (companyEntity.getInternship() != null && !companyEntity.getInternship().getStudent().getTutor().getId().equals(tutorId)) {
                hibernate.endTransaction(session);
                return Optional.empty();
            }
        } catch (NullPointerException e){
            hibernate.endTransaction(session);
            return Optional.empty();
        }

        hibernate.endTransaction(session);
        return Optional.of(companyEntity).map(CompanyEntity::toModel);
    }

    @Override
    public Optional<Company> insertCompany(String name, String address) {
        Session session = hibernate.beginTransaction();

        CompanyEntity companyEntity = new CompanyEntity(name, address);
        session.persist(companyEntity);

        hibernate.endTransaction(session);

        return Optional.of(companyEntity).map(CompanyEntity::toModel);
    }

    @Override
    public boolean updateCompany(UUID tutorId, UUID id, String name, String address) {
        Session session = hibernate.beginTransaction();

        CompanyEntity companyEntity = session.get(CompanyEntity.class, id);
        try {
            // If the company already has an internship, only return it is the current user owns it
            if (companyEntity.getInternship() != null && !companyEntity.getInternship().getStudent().getTutor().getId().equals(tutorId)) {
                hibernate.endTransaction(session);
                return false;
            }
        } catch (NullPointerException e){
            hibernate.endTransaction(session);
            return false;
        }

        session.evict(companyEntity);
        companyEntity.setName(name);
        companyEntity.setAddress(address);
        session.merge(companyEntity);

        hibernate.endTransaction(session);
        return true;
    }
}
