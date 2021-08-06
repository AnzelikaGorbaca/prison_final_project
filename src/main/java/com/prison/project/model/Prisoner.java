package com.prison.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static javax.persistence.FetchType.EAGER;

@Data
@Entity
@Table(name = "Prisoner")
public class Prisoner {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "prisoner_id")
    private Long id;
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Surname is required")
    private String surname;
    @NotBlank(message = "Personal code is required")
    @Column(unique=true)
    @Pattern(regexp="^([0-9]{6}(\\-)[0-9]{5})$",message="Personal code format 000000-00000")
    private String personalCode;
    @NotBlank(message = "Address is required")
    private String address;

    private String photo;

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
    private List<Crime> crimes;



    @OneToOne
    @JoinColumn(name = "punishment_id")
    private Punishment punishment;
}

