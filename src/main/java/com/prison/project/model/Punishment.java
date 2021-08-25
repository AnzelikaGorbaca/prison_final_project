package com.prison.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class Punishment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "punishment_id")
    private Long id;
    @NotNull(message = "Imprisonment duration is required")
    @Column(name = "imprisonment_months")
    private Integer imprisonmentMonths;
}
