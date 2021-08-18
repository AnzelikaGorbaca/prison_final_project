package com.prison.project.utilities;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;

public class FileUploadUtil {
//    public static void saveFile(String uploadDir, String fileName,
//                                MultipartFile multipartFile) throws IOException {
//        Path uploadPath = Paths.get(uploadDir);
//
//        if (!Files.exists(uploadPath)) {
//            Files.createDirectories(uploadPath);
//        }
//        try (InputStream inputStream = multipartFile.getInputStream()) {
//            Path filePath = uploadPath.resolve(fileName);
//            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
//        } catch (IOException ioe) {
//            throw new IOException("Could not save image file: " + fileName, ioe);
//        }
//
//    }
//
//    public static void deleteFile(Path path) {
//
//        try {
//            Files.delete(path);
//        } catch (NoSuchFileException ex) {
//            System.out.printf("No such file or directory: %s\n", path);
//        } catch (DirectoryNotEmptyException ex) {
//            System.out.printf("Directory %s is not empty\n", path);
//        } catch (IOException ex) {
//            System.out.println(ex);
//        }
//    }
}
