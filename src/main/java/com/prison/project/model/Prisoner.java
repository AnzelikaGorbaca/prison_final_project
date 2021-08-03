package com.prison.project.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@Entity
@Table(name = "Prisoner")
public class Prisoner {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "prisoner_id")
    private Long id;
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Surname is required")
    private String surname;
    @NotBlank(message = "Personal code is required")
    @Column(unique=true) //message = "This prisoner is already in the system")
    @Length(max=12,min=11, message = "Personal code format 000000-00000")
    private String personalCode;
    @NotBlank(message = "Address is required")
    private String address;


//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(name = "prisoner_crime_punishment", joinColumns = @JoinColumn(name = "prisoner_id"),
//            inverseJoinColumns = @JoinColumn(name = "crime_id"))
//    private Set<Crime> crimes;


//
//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(name = "prisoner_crime_punishment", joinColumns = @JoinColumn(name = "prisoner_id"),
//            inverseJoinColumns = @JoinColumn(name = "punishment_id"))
//
//      private Punishment punishment;


}
