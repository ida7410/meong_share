package com.ms.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileManagerService {
    
//    public static final String FILE_UPLOAD_PATH = "C:\\megastudy\\6_spring_project\\MEONG_SHARE\\ms_workspace\\images/";
    public static final String FILE_UPLOAD_PATH = "D:\\hyeonbeen\\6_spring project\\MEONGSHARE\\ms_workspace/images/";
    
    public String saveFile(String loginId, MultipartFile file) {
        // directory name: {loginId}_{current time in milli sec}
        String directoryName = loginId + "_" + System.currentTimeMillis();
        
        // file path: ...MEONGSHARE\\ms_workspace/images/{loginId}_{current time in milli sec}
        String filePath = FILE_UPLOAD_PATH + directoryName;
        
        // create directory
        File directory = new File(filePath);
        if (!directory.mkdir()) { // if failed
            return null;
        }
        
        try {
            byte[] bytes = file.getBytes();
			
			//파일명 암호화
			String origName = new String(file.getOriginalFilename().getBytes("8859_1"),"UTF-8");
			String ext = origName.substring(origName.lastIndexOf(".")); // 확장자
			String saveFileName = getUuid() + ext;
			
			Path path = Paths.get(filePath + "/" + saveFileName);
			Files.write(path, bytes);
			
			return "/images/" + directoryName + "/" + saveFileName;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    public static String getUuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
