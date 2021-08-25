package com.prison.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaffSearch {

    private String name;
    private String surname;
    private String occupation;
    private String personalCode;
    private String phoneNumber;
    private String address;

    public String getName() {
        return Strings.isNotBlank(name) ? name : null;
    }

    public String getSurname() {
        return Strings.isNotBlank(surname) ? surname : null;
    }

    public String getOccupation() {
        return Strings.isNotBlank(occupation) ? occupation : null;
    }

    public String getPersonalCode() {
        return Strings.isNotBlank(personalCode) ? personalCode : null;
    }

    public String getPhoneNumber() {
        return Strings.isNotBlank(phoneNumber) ? phoneNumber : null;
    }

    public String getAddress() {
        return Strings.isNotBlank(address) ? address : null;
    }

}
