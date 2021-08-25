package com.prison.project.service.photo;

import com.prison.project.model.Prisoner;
import com.prison.project.service.prisoner.CreatePrisonerService;
import com.prison.project.service.prisoner.GetPrisonerService;
import com.prison.project.service.prisoner.UpdatePrisonerService;
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
public class PhotoServicePrisoner {

    private final UpdatePrisonerService updatePrisonerService;
    private final CreatePrisonerService createPrisonerService;
    private final GetPrisonerService getPrisonerService;
    private final PhotoServiceDeletePhoto photoServiceDeletePhoto;
    private final PhotoServiceAddPhoto photoServiceAddPhoto;

    public void uploadPhoto(Long id, Prisoner prisoner, MultipartFile multipartFile) {

        Prisoner savedPrisoner = updatePrisonerService.updatePrisoner(id, prisoner);
        photoServiceDeletePhoto.deletePhoto(Paths.get("photos/" + "prisoner_" + id + "/" + savedPrisoner.getPhoto()));

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        String uploadDir = "photos/" + "prisoner_" + id;

        if (!fileName.isEmpty()) savedPrisoner.setPhoto(fileName);
        {
            photoServiceAddPhoto.savePhoto(uploadDir, fileName, multipartFile);
            updatePrisonerService.updatePrisoner(id, prisoner);
        }
    }

    public void uploadPhotoRegister(Prisoner prisoner, MultipartFile multipartFile) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        prisoner.setPhoto(fileName);
        Prisoner savedPrisoner = createPrisonerService.registerPrisoner(prisoner);
        String uploadDir = "photos/" + "prisoner_" + savedPrisoner.getId();
        photoServiceAddPhoto.savePhoto(uploadDir, fileName, multipartFile);
    }

    public void deletePhoto(Long id) {
        Path path = Paths.get("photos/" + "prisoner_" + id + "/" + getPrisonerService.getPrisonerById(id).getPhoto());
        photoServiceDeletePhoto.deletePhoto(path);
        Path dir = Paths.get("photos/" + "prisoner_" + id);
        photoServiceDeletePhoto.deletePhoto(dir);
    }

}


