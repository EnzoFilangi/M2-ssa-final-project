package com.ssa.team3.backend.model.entities;

import com.ssa.team3.backend.model.models.Student;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
public class StudentEntity {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "group", nullable = false)
    private String group;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private Set<InternshipEntity> internships;

    public StudentEntity() {}

    /**
     * Casts this entity to the relevant model
     *
     * Also casts the relations of this entity if fetchType is EAGER, and ignores them if fetchType is LAZY
     * @param fetchType
     * @return
     */
    public Student toModel(jakarta.persistence.FetchType fetchType) {
        switch (fetchType){
            case EAGER:
                return new Student(id, firstName, lastName, group, internships.stream().map(internship -> internship.toModel(FetchType.LAZY)).collect(Collectors.toSet()));
            case LAZY:
            default:
                return new Student(id, firstName, lastName, group, new HashSet<>());
        }
    }

    /**
     * Casts this entity to the relevant model without casting its relations
     * @return
     */
    public Student toModel() {
        return toModel(FetchType.LAZY);
    }
}
