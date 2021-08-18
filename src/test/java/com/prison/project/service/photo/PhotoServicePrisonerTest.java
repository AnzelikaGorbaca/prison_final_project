package com.prison.project.service.photo;

import com.prison.project.model.Crime;
import com.prison.project.model.Prisoner;
import com.prison.project.model.Punishment;
import com.prison.project.service.prisoner.CreatePrisonerService;
import com.prison.project.service.prisoner.GetPrisonerService;
import com.prison.project.service.prisoner.UpdatePrisonerService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PhotoServicePrisonerTest {

    @InjectMocks
    private PhotoServicePrisoner photoServicePrisoner;
    @Mock
    private UpdatePrisonerService updatePrisonerService;
    @Mock
    private CreatePrisonerService createPrisonerService;
    @Mock
    private GetPrisonerService getPrisonerService;
    @Mock
    private PhotoServiceDeletePhoto photoServiceDeletePhoto;
    @Mock
    private PhotoServiceAddPhoto photoServiceAddPhoto;


    private final String start = "2021-08-13";
    private final LocalDate startDate = LocalDate.parse(start);
    private final String end = "2022-01-13";
    private final LocalDate endDate = LocalDate.parse(end);

    private final List<Crime> crimes = Arrays.asList(new Crime(2L, "Robbery"), new Crime(2L, "Murder"));
    private final Punishment punishment = new Punishment(1L, 5);
    private final Prisoner prisoner = new Prisoner(27L, "Janis", "Ozolins", "310856 - 10605",
            "Rigas iela 4-5", startDate, endDate, "5696649401_2_4_1.jpg",
            null, "In Prison", crimes, punishment, punishment.getId(), "Murder, Robbery, Awful Cook");

    MultipartFile multipartFile = new MultipartFile() {
        @Override
        public String getName() {
            return "5696649401_2_4_1.jpg";
        }

        @Override
        public String getOriginalFilename() {
            return "5696649401_2_4_1.jpg";
        }

        @Override
        public String getContentType() {
            return "jpg";
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public long getSize() {
            return 5;
        }

        @Override
        public byte[] getBytes() throws IOException {
            return new byte[50];
        }

        @Override
        public InputStream getInputStream() throws IOException {
            File file = new File("src/test/resources/5696649401_2_4_1.jpg");
            FileInputStream input = new FileInputStream(file);
            return input;
        }

        @Override
        public void transferTo(File dest) throws IOException, IllegalStateException {
            new FileOutputStream(dest).write(getBytes());
        }
    };


    @Test
    void checkPhotoForErrorsAndUploadWhenNoErrors() {
        Long id = 27L;
        when(updatePrisonerService.updatePrisoner(id, prisoner)).thenReturn(prisoner);
        doNothing().when(photoServiceDeletePhoto).deletePhoto(Paths.get("photos/" + "prisoner_" + id + "/" + prisoner.getPhoto()));

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        String uploadDir = "photos/" + "prisoner_" + id;
        prisoner.setPhoto(fileName);
        doNothing().when(photoServiceAddPhoto).savePhoto(uploadDir, fileName, multipartFile);
        when(updatePrisonerService.updatePrisoner(id, prisoner)).thenReturn(prisoner);
        boolean check = photoServicePrisoner.checkPhotoForErrorsAndUpload(id, prisoner, multipartFile);

        assertFalse(check);
        verify(updatePrisonerService, times(2)).updatePrisoner(id, prisoner);
        verify(photoServiceDeletePhoto).deletePhoto(Paths.get("photos/" + "prisoner_" + id + "/" + prisoner.getPhoto()));
        verify(photoServiceAddPhoto).savePhoto(uploadDir, fileName, multipartFile);
    }

    @Test
    void uploadPhotoRegister() {
        when(createPrisonerService.registerPrisoner(prisoner)).thenReturn(prisoner);
        String uploadDir = "photos/" + "prisoner_" + prisoner.getId();
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        doNothing().when(photoServiceAddPhoto).savePhoto(uploadDir, fileName,multipartFile);

        photoServicePrisoner.uploadPhotoRegister(prisoner,multipartFile);
        verify(createPrisonerService).registerPrisoner(prisoner);
        verify(photoServiceAddPhoto).savePhoto(uploadDir, fileName,multipartFile);
    }

    @Test
    void deletePhoto() {

        Long id = 27L;
        when(getPrisonerService.getPrisonerById(27L)).thenReturn(prisoner);
        Path path = Paths.get("photos/" + "prisoner_" + id + "/" + prisoner.getPhoto());
        doNothing().when(photoServiceDeletePhoto).deletePhoto(path);
        Path dir = Paths.get("photos/" + "prisoner_" + id);
        doNothing().when(photoServiceDeletePhoto).deletePhoto(dir);

        photoServicePrisoner.deletePhoto(id);


        verify(photoServiceDeletePhoto).deletePhoto(path);
        verify(photoServiceDeletePhoto).deletePhoto(dir);
        verify(getPrisonerService).getPrisonerById(27L);

    }
}