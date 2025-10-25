package com.gogo.utilisateur_service.model;

import com.gogo.utilisateur_service.enums.RoleName;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Data
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Enumerated(EnumType.STRING)
    private RoleName role; // ou enum NomRole { ADMIN, RECRUTEUR }


}
