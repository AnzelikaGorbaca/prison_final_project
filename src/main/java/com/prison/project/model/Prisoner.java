package com.prison.project.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static javax.persistence.FetchType.EAGER;

@Data
@Entity
@Table(name = "Prisoner")
public class Prisoner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prisoner_id")
    private Long id;
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Surname is required")
    private String surname;
    @NotBlank(message = "Personal code is required")
    @Length(max = 11, min = 11)
    private String personalCode;
    @NotBlank(message = "Address is required")
    private String address;

//    @ManyToMany(fetch = EAGER)
//    @JoinTable(
//            name = "crime_prisoner",
//            joinColumns = @JoinColumn(
//                    name = "prisoner_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(
//                    name = "crime_id", referencedColumnName = "id"
//            )
//    )
//    private List<Crime> crimes;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "prisoner_crime",
            joinColumns = @JoinColumn(
                    name = "prisoner_id"),
            inverseJoinColumns = @JoinColumn(name = "crime_id"))
    private Set<Crime> crimes;

    @OneToOne
    @JoinColumn(name = "punishment_id")
    private Punishment punishment;
}
