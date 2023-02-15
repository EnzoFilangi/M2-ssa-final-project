package com.ssa.team3.backend.model.entities;

import com.ssa.team3.backend.model.models.Company;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
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

    public CompanyEntity() {}

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
