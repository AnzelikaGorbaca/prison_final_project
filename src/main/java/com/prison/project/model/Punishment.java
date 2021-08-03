package com.prison.project.model;

import javax.persistence.*;

@Entity
public class Punishment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "punishment_id")
    private Long id;

}
