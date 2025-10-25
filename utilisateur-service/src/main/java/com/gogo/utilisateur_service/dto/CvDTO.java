package com.gogo.utilisateur_service.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CvDTO {
    private Long id;
    private String titre;
    private String fichierUrl; // url retournée après upload (ex: http://.../uploads/cv123.pdf)
    private Integer  version;    // facultatif
    private LocalDateTime dateDepot;
}

