package com.prison.project.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    @Column(unique = true)
    @Pattern(regexp = "^([0-9]{6}(\\-)[0-9]{5})$", message = "Personal code format 000000-00000")
    private String personalCode;
    @NotBlank(message = "Address is required")
    private String address;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "DATE")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private String photo;
    private Boolean inPrison;
    @Transient
    private String status;


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


    @ManyToMany
    @JoinTable(name = "prisoner_crime",
            joinColumns = @JoinColumn(
                    name = "prisoner_id"),
            inverseJoinColumns = @JoinColumn(name = "crime_id"))

    private List<Crime> crimes;


    @OneToOne
    @JoinColumn(name = "punishment_id")
    private Punishment punishment;

    @Transient
//    @NotNull(message = "Punishment is required")
    private Long punishmentId;
    @Transient
    private String crimesJson;

    @Transient
    public String getPhotoImagePath() {
        if (photo == null || id == null) return null;
        return "/photos/" + "prisoner_" + id + "/" + photo;
    }

    @Transient
    public String crimeDescriptions() {
        return emptyIfNullStream(crimes)
                .map(Crime::getCrimeDescription)
                .collect(Collectors.joining(", "));
    }

    private Stream<Crime> emptyIfNullStream(List<Crime> crimes) {
        return crimes.isEmpty() ? Stream.empty() : crimes.stream();
    }

}
