package com.prison.project.service.prisoner;

import com.prison.project.model.Prisoner;
import com.prison.project.utilities.FileUploadUtil;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.exceptions.TemplateInputException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.Objects;

@Service
@AllArgsConstructor
public class PhotoServicePrisoner {

    private final UpdatePrisonerService updatePrisonerService;
    private final CreatePrisonerService createPrisonerService;
    private final GetPrisonerService getPrisonerService;

    public boolean checkPhotoForErrorsAndUpload(Long id, Prisoner prisoner, MultipartFile multipartFile) {

        Prisoner savedPrisoner = updatePrisonerService.updatePrisoner(id, prisoner);
        FileUploadUtil.deleteFile(Paths.get("photos/" + "prisoner_" + id + "/" + savedPrisoner.getPhoto()));

        try {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            String uploadDir = "photos/" + "prisoner_" + id;

            if (!fileName.isEmpty()) savedPrisoner.setPhoto(fileName);
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            updatePrisonerService.updatePrisoner(id, prisoner);
        } catch (RuntimeException | IOException e) {
            return true;
        }
        return false;
    }

    public void uploadPhotoRegister(Prisoner prisoner, MultipartFile multipartFile) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        prisoner.setPhoto(fileName);
        Prisoner savedPrisoner = createPrisonerService.registerPrisoner(prisoner);
        String uploadDir = "photos/" + "prisoner_" + savedPrisoner.getId();
        saveFile(uploadDir, fileName, multipartFile);
    }

    public void deletePhoto (Long id){
        Path path = Paths.get("photos/" + "prisoner_" + id + "/" + getPrisonerService.getPrisonerById(id).getPhoto());
        FileUploadUtil.deleteFile(path);
        Path dir = Paths.get("photos/" + "prisoner_" + id);
        deleteFile(dir);
    }


    private void saveFile(String uploadDir, String fileName,
                          MultipartFile multipartFile) {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new RuntimeException("Could not save image file: " + fileName, ioe);
        }

    }

    private void deleteFile(Path path) {

        try {
            Files.delete(path);
        } catch (NoSuchFileException ex) {
            System.out.printf("No such file or directory: %s\n", path);
        } catch (DirectoryNotEmptyException ex) {
            System.out.printf("Directory %s is not empty\n", path);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}


