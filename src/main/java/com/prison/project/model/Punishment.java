package com.prison.project.model;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Data
@Entity
@Table
public class Punishment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "punishment_id")
    private Long id;
    @NotNull(message = "Imprisonment duration is required")
    @Column(name="imprisonment_months")
    private Integer imprisonmentMonths;
}
