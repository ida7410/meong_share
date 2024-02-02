package com.ms.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileManagerService {
    
//    public static final String FILE_UPLOAD_PATH = "C:\\megastudy\\6_spring_project\\MEONG_SHARE\\ms_workspace\\images/";
    public static final String FILE_UPLOAD_PATH = "C:\\megastudy\\6_spring_project\\MEONG_SHARE\\ms_workspace\\images/";
    
    public String saveFile(String loginId, MultipartFile file) {
        // create directory
        String directoryName = loginId + "_" + System.currentTimeMillis();
        
        String filePath = FILE_UPLOAD_PATH + directoryName;
        
        File directory = new File(filePath);
        if (!directory.mkdir()) {
            return null;
        }
        
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(filePath + "/" + file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        
        return "/images/" + directoryName + "/" + file.getOriginalFilename();
    }
    
}
