package com.prison.project.service.photo;

import com.prison.project.model.Staff;
import com.prison.project.service.staff.CreateStaffService;
import com.prison.project.service.staff.GetStaffService;
import com.prison.project.service.staff.UpdateStaffService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PhotoServiceStaffTest {

    @InjectMocks
    private PhotoServiceStaff photoServiceStaff;

    @Mock
    private UpdateStaffService updateStaffService;
    @Mock
    private CreateStaffService createStaffService;
    @Mock
    private PhotoServiceAddPhoto photoServiceAddPhoto;
    @Mock
    private PhotoServiceDeletePhoto photoServiceDeletePhoto;
    @Mock
    private GetStaffService getStaffService;


    Staff sampleStaff = new Staff(1L, "Elvis", "Presley", "GUARD", "123456-12345",
            "+371212345678", "AddressConsistingOf10", "5696649401_2_4_1.jpg");

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
    void uploadPhoto() {
        Long id = 1L;
        when(updateStaffService.updateStaff(id, sampleStaff)).thenReturn(sampleStaff);
        doNothing().when(photoServiceDeletePhoto).deletePhoto(Paths.get("photos/" + "staff_" + id + "/" + sampleStaff.getPhoto()));

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        String uploadDir = "photos/" + "staff_" + id;

        sampleStaff.setPhoto(fileName);
        doNothing().when(photoServiceAddPhoto).savePhoto(uploadDir, fileName, multipartFile);

        when(updateStaffService.updateStaff(id, sampleStaff)).thenReturn(sampleStaff);
        photoServiceStaff.uploadPhoto(id, sampleStaff, multipartFile);


        verify(updateStaffService, times(2)).updateStaff(id, sampleStaff);
        verify(photoServiceDeletePhoto).deletePhoto(Paths.get("photos/" + "staff_" + id + "/" + sampleStaff.getPhoto()));
        verify(photoServiceAddPhoto).savePhoto(uploadDir, fileName, multipartFile);
    }

    @Test
    void uploadPhotoRegister() {

        when(createStaffService.registerStaff(sampleStaff)).thenReturn(sampleStaff);
        String uploadDir = "photos/" + "staff_" + sampleStaff.getId();
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        doNothing().when(photoServiceAddPhoto).savePhoto(uploadDir, fileName, multipartFile);

        photoServiceStaff.uploadPhotoRegister(sampleStaff, multipartFile);
        verify(createStaffService).registerStaff(sampleStaff);
        verify(photoServiceAddPhoto).savePhoto(uploadDir, fileName, multipartFile);
    }

    @Test
    void deletePhoto() {

        Long id = 1L;
        when(getStaffService.findStaffById(id)).thenReturn(sampleStaff);
        Path path = Paths.get("photos/" + "staff_" + id + "/" + sampleStaff.getPhoto());
        doNothing().when(photoServiceDeletePhoto).deletePhoto(path);
        Path dir = Paths.get("photos/" + "staff_" + id);
        doNothing().when(photoServiceDeletePhoto).deletePhoto(dir);

        photoServiceStaff.deletePhoto(id);


        verify(photoServiceDeletePhoto).deletePhoto(path);
        verify(photoServiceDeletePhoto).deletePhoto(dir);
        verify(getStaffService).findStaffById(id);
    }
}