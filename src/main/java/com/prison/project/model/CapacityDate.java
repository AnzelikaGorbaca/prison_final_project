package com.prison.project.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@Component
public class CapacityDate {


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate distinctDate;
}
