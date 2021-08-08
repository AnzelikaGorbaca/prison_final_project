package com.prison.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@Table (name = "crimes")
@AllArgsConstructor
public class Crime {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "crime_id")
    private Long id;
    @NotBlank(message = "Crime Description required")
    @Column (name = "Crime_description")
    private String crimeDescription;



}
