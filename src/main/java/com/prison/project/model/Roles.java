package com.prison.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "roles")
@Entity
@AllArgsConstructor
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "role_id")
    private Long roleID;
    @Column(name ="role_name")
    private String roleName;
}
