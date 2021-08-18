package com.prison.project.service.photo;

import com.prison.project.model.Prisoner;
import com.prison.project.service.prisoner.CreatePrisonerService;
import com.prison.project.service.prisoner.GetPrisonerService;
import com.prison.project.service.prisoner.UpdatePrisonerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.*;
import java.util.Objects;

@Service
@AllArgsConstructor
public class PhotoServicePrisoner {

    private final UpdatePrisonerService updatePrisonerService;
    private final CreatePrisonerService createPrisonerService;
    private final GetPrisonerService getPrisonerService;
    private final PhotoServiceDeletePhoto photoServiceDeletePhoto;
    private final PhotoServiceAddPhoto photoServiceAddPhoto;

    public boolean checkPhotoForErrorsAndUpload(Long id, Prisoner prisoner, MultipartFile multipartFile) {

        Prisoner savedPrisoner = updatePrisonerService.updatePrisoner(id, prisoner);
//        FileUploadUtil.deleteFile(Paths.get("photos/" + "prisoner_" + id + "/" + savedPrisoner.getPhoto()));
          photoServiceDeletePhoto.deletePhoto(Paths.get("photos/" + "prisoner_" + id + "/" + savedPrisoner.getPhoto()));
        try {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            String uploadDir = "photos/" + "prisoner_" + id;

            if (!fileName.isEmpty()) savedPrisoner.setPhoto(fileName);
//            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            photoServiceAddPhoto.savePhoto(uploadDir,fileName,multipartFile);
            updatePrisonerService.updatePrisoner(id, prisoner);
        } catch (RuntimeException e) {
            return true;
        }
        return false;
    }

    public void uploadPhotoRegister(Prisoner prisoner, MultipartFile multipartFile) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        prisoner.setPhoto(fileName);
        Prisoner savedPrisoner = createPrisonerService.registerPrisoner(prisoner);
        String uploadDir = "photos/" + "prisoner_" + savedPrisoner.getId();
        photoServiceAddPhoto.savePhoto(uploadDir, fileName, multipartFile);
    }

    public void deletePhoto (Long id){
        Path path = Paths.get("photos/" + "prisoner_" + id + "/" + getPrisonerService.getPrisonerById(id).getPhoto());
//        FileUploadUtil.deleteFile(path);
        photoServiceDeletePhoto.deletePhoto(path);
        Path dir = Paths.get("photos/" + "prisoner_" + id);
        photoServiceDeletePhoto.deletePhoto(dir);
    }



}


