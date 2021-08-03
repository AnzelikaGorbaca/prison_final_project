package com.prison.project.model;

import javax.persistence.*;

@Entity
public class Crime {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "crime_id")
    private Long id;
}
