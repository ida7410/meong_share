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
    
    public static final String FILE_UPLOAD_PATH = "C:\\megastudy\\6_spring_project\\MEONG_SHARE\\ms_workspace\\images/";
//    public static final String FILE_UPLOAD_PATH = "D:\\hyeonbeen\\6_spring project\\MEONGSHARE\\ms_workspace/images/";

    public String saveFile(MultipartFile file, String key, String type) {
        // directory name: {type}/{key}
        String directoryName = type + "/" + key;

        // file path: ...MEONGSHARE\\ms_workspace/images/{type}/{key}
        String filePath = FILE_UPLOAD_PATH + directoryName;

        // create directory
        File directory = new File(filePath);
        if (!directory.exists() && !directory.mkdir()) { // if failed
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
