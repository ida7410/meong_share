package com.ms.common;

import com.google.cloud.storage.*;
import com.google.cloud.storage.Storage.SignUrlOption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class FileManagerService {

    @Value("${google.bucket.name}")
    private String BUCKET_NAME;

    private final Storage storage = StorageOptions.getDefaultInstance().getService();

    public static String getUuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

    public String saveFileGcs(MultipartFile file, String key, String type, String ext) throws IOException {
        try {
            String uuid = getUuid();
            String objectName = type + "/" + key + "/" + uuid + "." + ext;

            BlobId blobId = BlobId.of(BUCKET_NAME, objectName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                    .setContentType(file.getContentType())
                    .build();

            log.info("Object name: {}", objectName);

            assert storage != null;
            storage.create(blobInfo, file.getBytes());

            String imageUrl = String.format("/image/%s/%s/%s.%s", type, key, uuid, ext);
            return imageUrl;
        }
        catch(Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }
}
