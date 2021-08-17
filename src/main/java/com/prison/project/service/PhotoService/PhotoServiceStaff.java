package com.prison.project.service.PhotoService;

import com.prison.project.model.Prisoner;
import com.prison.project.model.Staff;
import com.prison.project.service.prisoner.UpdatePrisonerService;
import com.prison.project.service.staff.CreateStaffService;
import com.prison.project.service.staff.GetStaffService;
import com.prison.project.service.staff.UpdateStaffService;
import com.prison.project.utilities.FileUploadUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Service
@AllArgsConstructor
public class PhotoServiceStaff {
    private final UpdateStaffService updateStaffService;
    private final CreateStaffService createStaffService;
    private final PhotoServiceAddPhoto photoServiceAddPhoto;
    private final GetStaffService getStaffService;

    public boolean checkPhotoForErrorsAndUpload(Long id, Staff staff, MultipartFile multipartFile) {
        Staff savedStaff = updateStaffService.updateStaff(id, staff);
        FileUploadUtil.deleteFile(Paths.get("photos/" + "staff_" + id + "/" + savedStaff.getPhoto()));

        try {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            String uploadDir = "photos/" + "staff_" + id;
            if (!fileName.isEmpty()) savedStaff.setPhoto(fileName);
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            updateStaffService.updateStaff(id, staff);
        } catch (RuntimeException | IOException e) {
            e.printStackTrace();
            return true;
        }
        return false;
    }


    public void uploadPhotoRegister(Staff staff, MultipartFile multipartFile) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        staff.setPhoto(fileName);
        Staff savedStaff = createStaffService.registerStaff(staff);
        String uploadDir = "photos/" + "staff_" + savedStaff.getId();
        photoServiceAddPhoto.savePhoto(uploadDir, fileName, multipartFile);
    }


    public void deletePhoto (Long id){
        Path path = Paths.get("photos/" + "staff_" + id + "/" + getStaffService.findStaffById(id).getPhoto());
        FileUploadUtil.deleteFile(path);
        Path dir = Paths.get("photos/" + "staff_" + id);
        FileUploadUtil.deleteFile(dir);
    }
}
