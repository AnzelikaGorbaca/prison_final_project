package com.prison.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Fugitive {

    private String title;
    private String details;
    private String caution;
    private String image;
    private String sex;
    private String remarks;
    private String reward;
    private String message;
    private String pdfLink;

}


