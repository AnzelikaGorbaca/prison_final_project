package com.prison.project.exception;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class FileSizeExceptionAdvice {

    @Value(("${server.port}"))
    private String port;


    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleError2(MaxUploadSizeExceededException e,
                               RedirectAttributes redirectAttributes,
                               HttpServletRequest request) {
        String originalUri = request.getHeader("referer");
        String portPath = port + "/";
        int index = originalUri.indexOf(portPath);
        int endIndex = index + portPath.length();

        String path = originalUri.substring(endIndex);
        redirectAttributes.addFlashAttribute("message", e.getCause().getMessage());
        return "forward:" + path;

    }
}
