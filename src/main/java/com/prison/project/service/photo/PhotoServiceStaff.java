package com.prison.project.service.photo;

import com.prison.project.model.Staff;
import com.prison.project.service.staff.CreateStaffService;
import com.prison.project.service.staff.GetStaffService;
import com.prison.project.service.staff.UpdateStaffService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Transactional
@Service
@AllArgsConstructor
public class PhotoServiceStaff {
    private final UpdateStaffService updateStaffService;
    private final CreateStaffService createStaffService;
    private final PhotoServiceAddPhoto photoServiceAddPhoto;
    private final PhotoServiceDeletePhoto photoServiceDeletePhoto;
    private final GetStaffService getStaffService;

    public void uploadPhoto(Long id, Staff staff, MultipartFile multipartFile) {
        Staff savedStaff = updateStaffService.updateStaff(id, staff);
        photoServiceDeletePhoto.deletePhoto(Paths.get("photos/" + "staff_" + id + "/" + savedStaff.getPhoto()));

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        String uploadDir = "photos/" + "staff_" + id;

        if (!fileName.isEmpty()) savedStaff.setPhoto(fileName);
        {
            photoServiceAddPhoto.savePhoto(uploadDir, fileName, multipartFile);
            updateStaffService.updateStaff(id, staff);
        }

    }

    public void uploadPhotoRegister(Staff staff, MultipartFile multipartFile) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        staff.setPhoto(fileName);
        Staff savedStaff = createStaffService.registerStaff(staff);
        String uploadDir = "photos/" + "staff_" + savedStaff.getId();
        photoServiceAddPhoto.savePhoto(uploadDir, fileName, multipartFile);
    }

    public void deletePhoto(Long id) {
        Path path = Paths.get("photos/" + "staff_" + id + "/" + getStaffService.findStaffById(id).getPhoto());
        photoServiceDeletePhoto.deletePhoto(path);
        Path dir = Paths.get("photos/" + "staff_" + id);
        photoServiceDeletePhoto.deletePhoto(dir);
    }
}
