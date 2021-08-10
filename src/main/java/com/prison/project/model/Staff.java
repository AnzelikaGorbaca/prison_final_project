package com.prison.project.model;

import com.prison.project.exception.BadRequestException;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;


@Data
@Entity
@Table(name="staff")
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "staff_id")
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Surname is required")
    private String surname;

    @NotBlank(message = "Occupation is required")
    private String occupation;

    @NotBlank(message = "Personal code is required")
    @Column(unique = true)
    @Pattern(regexp = "^([0-9]{6}(\\-)[0-9]{5})$", message = "Personal code format 000000-00000")
    private String personalCode;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^((\\+)371[0-9]{8})$", message = "Phone number format +37100000000")
    private String phoneNumber;

    @NotBlank(message = "Address is required")
    @Length(min = 10, message = "Please enter full address")
    private String address;

    private String photo;

    public String getStaffPhotoImagePath() {
        if (photo == null || id == null) return null;

        return "/photos/" +"staff_" + id + "/" + photo;
    }
}