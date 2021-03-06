package com.prison.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.format.annotation.DateTimeFormat;;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrisonerSearch {

    private String name;
    private String surname;
    private String personalCode;
    private String address;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private List<Crime> crimes;
    private Long punishmentId;
    private Punishment punishment;
    private String crimesJson;
    private String status;

    public String getName() {
        return Strings.isNotBlank(name) ? name : null;
    }

    public String getSurname() {
        return Strings.isNotBlank(surname) ? surname : null;
    }

    public String getPersonalCode() {
        return Strings.isNotBlank(personalCode) ? personalCode : null;
    }

    public String getAddress() {
        return Strings.isNotBlank(address) ? address : null;
    }

    public String getStatus() {
        return Strings.isNotBlank(status) ? status : null;
    }

    public String getCrimesJson() {
        return Strings.isNotBlank(crimesJson) ? crimesJson : null;
    }

}
