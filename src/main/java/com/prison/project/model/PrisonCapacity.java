package com.prison.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
public class PrisonCapacity {

    private final Integer capacity = 100;

}
