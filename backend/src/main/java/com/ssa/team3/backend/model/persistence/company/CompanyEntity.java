package com.ssa.team3.backend.model.persistence.company;

import com.ssa.team3.backend.model.persistence.internship.InternshipEntity;
import com.ssa.team3.backend.model.domain.company.Company;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    private Set<InternshipEntity> internships;

    public CompanyEntity(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public CompanyEntity() {}

    public CompanyEntity(UUID id, String name, String address, Set<InternshipEntity> internships) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.internships = internships;
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
                return new Company(id, name, address, internships.stream().map(internship -> internship.toModel(FetchType.LAZY)).collect(Collectors.toSet()));
            case LAZY:
            default:
                return new Company(id, name, address, new HashSet<>());
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
