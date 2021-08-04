package com.prison.project.model;



import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table
public class Punishment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "punishment_id")
    private Long id;
//    @NotBlank(message = "Imprisonment duration is required")
    private int imprisonmentMonths;
}
