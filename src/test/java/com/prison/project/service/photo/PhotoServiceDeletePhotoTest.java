package com.prison.project.service.photo;

import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
@NoArgsConstructor
class PhotoServiceDeletePhotoTest {
    @InjectMocks
    PhotoServiceDeletePhoto photoServiceDeletePhoto;

    @Test
    void deletePhoto() {

        Path path =  Paths.get("photos/prisoner_27");
        photoServiceDeletePhoto.deletePhoto(path);
    }
}