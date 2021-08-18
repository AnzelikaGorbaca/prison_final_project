package com.prison.project.service.photo;


import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;

@Service
public class PhotoServiceDeletePhoto {


    public void deletePhoto(Path path) {

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
