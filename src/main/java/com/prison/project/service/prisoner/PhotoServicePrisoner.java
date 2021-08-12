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
import java.nio.file.Paths;
import java.util.Objects;

@Service
@AllArgsConstructor
public class PhotoServicePrisoner {

    private final UpdatePrisonerService updatePrisonerService;

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
}
