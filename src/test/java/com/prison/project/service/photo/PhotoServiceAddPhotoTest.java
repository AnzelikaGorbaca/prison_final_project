package com.prison.project.service.photo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
class PhotoServiceAddPhotoTest {

    @InjectMocks
    public PhotoServiceAddPhoto photoServiceAddPhoto;

    private final String uploadDir = "photos/prisoner_27";
    private final String fileName = "5696649401_2_4_1.jpg";
    private MultipartFile multipartFile = new MultipartFile() {
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
    void savePhotoWhenFailWithRuntimeException() {

        try {
            photoServiceAddPhoto.savePhoto(uploadDir, fileName, multipartFile);
            fail();
        } catch (RuntimeException e) {
            assertEquals(e.getMessage(), "Could not save image file: " + fileName);
        }
    }
}