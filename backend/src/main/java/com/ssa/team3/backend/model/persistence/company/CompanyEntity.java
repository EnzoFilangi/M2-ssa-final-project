package com.ssa.team3.backend.model.persistence.company;

import com.ssa.team3.backend.model.persistence.internship.InternshipEntity;
import com.ssa.team3.backend.model.domain.company.Company;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "COMPANIES")
public class CompanyEntity {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "internship_id", nullable = false)
    private InternshipEntity internship;

    public CompanyEntity(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public CompanyEntity() {}

    public CompanyEntity(UUID id, String name, String address, InternshipEntity internship) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.internship = internship;
    }

    /**
     * Casts this entity to the relevant model
     *
     * Also casts the relations of this entity if fetchType is EAGER, and ignores them if fetchType is LAZY
     * @param fetchType
     * @return
     */
    public Company toModel(jakarta.persistence.FetchType fetchType){
        switch (fetchType){
            case EAGER:
                return new Company(id, name, address, internship.toModel(FetchType.LAZY));
            case LAZY:
            default:
                return new Company(id, name, address, null);
        }
    }

    /**
     * Casts this entity to the relevant model without casting its relations
     * @return
     */
    public Company toModel(){
        return toModel(FetchType.LAZY);
    }
}
