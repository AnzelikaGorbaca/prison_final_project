package com.prison.project.service.photo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Path;
import java.nio.file.Paths;

@ExtendWith(MockitoExtension.class)
class PhotoServiceDeletePhotoTest {
    @InjectMocks
    PhotoServiceDeletePhoto photoServiceDeletePhoto;

    @Test
    void deletePhoto() {

        Path path =  Paths.get("photos/prisoner_27");
        photoServiceDeletePhoto.deletePhoto(path);
    }
}