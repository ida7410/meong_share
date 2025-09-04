package com.ms.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class FileRestController {

    @Autowired
    private FileManagerService fileManagerService;

//    @GetMapping("/getSignedUrl")
//    public ResponseEntity<Map<String, Object>> getSignedUrl(
//            @RequestParam Object key,
//            @RequestParam String ext,
//            @RequestParam String type
//    ) {
//        Map<String, Object> result = new HashMap<>();
//        try {
//            String filePath = fileManagerService.saveFileGcs((String) key, type, ext);
//
//            result.put("code", 200);
//            result.put("filePath", filePath);
//            result.put("result", "success");
//            return ResponseEntity.ok(result);
//        }
//        catch (Exception e) {
//            log.error(e.getMessage());
//            result.put("code", 300);
//            result.put("error", e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
//        }
//    }

    @PostMapping("/uploadToGcs")
    public ResponseEntity<Map<String, Object>> uploadToGcs(
            @RequestParam MultipartFile file,
            @RequestParam String key,
            @RequestParam String type,
            @RequestParam String ext
            ) {
        Map<String, Object> result = new HashMap<>();
        try {
            String publicUrl = fileManagerService.saveFileGcs(file, key, type, ext);

            result.put("code", 200);
            result.put("publicUrl", publicUrl);
            result.put("result", "success");
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 300);
            result.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

}
