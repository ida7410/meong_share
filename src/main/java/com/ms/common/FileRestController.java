package com.ms.common;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class FileRestController {

    @Autowired
    private FileManagerService fileManagerService;

    @Value("${google.bucket.name}")
    private String BUCKET_NAME;

    private final Storage storage = StorageOptions.getDefaultInstance().getService();

    @PostMapping("/uploadToGcs")
    public ResponseEntity<Map<String, Object>> uploadToGcs(
            @RequestParam MultipartFile file,
            @RequestParam String key,
            @RequestParam String type,
            @RequestParam String ext
            ) {
        Map<String, Object> result = new HashMap<>();
        try {
            String imageUrl = fileManagerService.saveFileGcs(file, key, type, ext);

            result.put("code", 200);
            result.put("imageUrl", imageUrl);
            result.put("result", "success");
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 300);
            result.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    @GetMapping("/image/{type}/{key}/{filename}")
    public ResponseEntity<byte[]> getImage(
            @PathVariable String type,
            @PathVariable String key,
            @PathVariable String filename) {
        try {
            String objectName = type + "/" + key + "/" + filename;
            BlobId blobId = BlobId.of(BUCKET_NAME, objectName);
            Blob blob = storage.get(blobId);

            if (blob == null) {
                return ResponseEntity.notFound().build();
            }

            byte[] content = blob.getContent();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(blob.getContentType()));

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(content);
        } catch (Exception e) {
            log.error("error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
