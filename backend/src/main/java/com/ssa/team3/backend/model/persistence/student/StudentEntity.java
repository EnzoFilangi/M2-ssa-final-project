package com.ssa.team3.backend.model.persistence.student;

import com.ssa.team3.backend.model.persistence.IAM.UserEntity;
import com.ssa.team3.backend.model.persistence.internship.InternshipEntity;
import com.ssa.team3.backend.model.domain.student.Student;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "STUDENTS")
public class StudentEntity {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "student_group", nullable = false)
    private String group;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<InternshipEntity> internships;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tutor_id", nullable = false)
    private UserEntity tutor;

    public StudentEntity(String firstName, String lastName, String group, UserEntity tutor) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.group = group;
        this.tutor = tutor;
    }

    public StudentEntity() {}

    public StudentEntity(UUID id, String firstName, String lastName, String group, UserEntity tutor, Set<InternshipEntity> internships) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.group = group;
        this.tutor = tutor;
        this.internships = internships;
    }

    /**
     * Casts this entity to the relevant model
     *
     * Also casts the relations of this entity if resolveRelations is true
     */
    public Student toModel(boolean resolveRelations) {
        if (resolveRelations) {
            return new Student(id, firstName, lastName, group, internships.stream().map(internship -> internship.toModel(false, true)).collect(Collectors.toSet()), tutor.toModel());
        }
        return new Student(id, firstName, lastName, group, new HashSet<>(), null);
    }

    /**
     * Casts this entity to the relevant model without casting its relations
     */
    public Student toModel() {
        return toModel(false);
    }

    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Set<InternshipEntity> getInternships() {
        return internships;
    }

    public void setInternships(Set<InternshipEntity> internships) {
        this.internships = internships;
    }

    public UserEntity getTutor() {
        return tutor;
    }

    public void setTutor(UserEntity tutor) {
        this.tutor = tutor;
    }
}
