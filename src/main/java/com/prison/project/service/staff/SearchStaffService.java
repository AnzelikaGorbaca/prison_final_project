package com.prison.project.service.staff;

import com.prison.project.model.Staff;
import com.prison.project.model.StaffSearch;
import com.prison.project.repository.StaffRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static org.springframework.data.domain.ExampleMatcher.matchingAll;

@Transactional
@Service
@AllArgsConstructor
public class SearchStaffService {

    private final StaffRepository staffRepository;

    public List<Staff> searchStaff(StaffSearch staffSearch) {
        Staff staff = new Staff();

        staff.setName(staffSearch.getName());
        staff.setSurname(staffSearch.getSurname());
        staff.setOccupation(staffSearch.getOccupation());
        staff.setPersonalCode(staffSearch.getPersonalCode());
        staff.setPhoneNumber(staffSearch.getPhoneNumber());
        staff.setAddress(staffSearch.getAddress());

        Example<Staff> staffExample = Example.of(staff, matchingAll().withIgnoreNullValues().withIgnoreCase());
        return staffRepository.findAll(staffExample);
    }
}
