package com.ms.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

@Component
@Slf4j
@ConditionalOnProperty(name = "app.cleanup.enabled", havingValue = "true")
public class Batch {

    @Scheduled(fixedRate = 86400000) // 24 hours in milliseconds
    public void cleanup() {
        Arrays.asList("../images/chat-images", "../images/profile-images", "../images/product-images")
                .forEach(this::removeEmptyFolders);
    }

    private void removeEmptyFolders(String pathStr) {
        try {
            Files.list(Paths.get(pathStr))
                    .filter(Files::isDirectory)
                    .filter(this::isEmpty)
                    .forEach(this::deleteFolder);
        } catch (Exception e) {
            log.error("Error cleaning: {}", pathStr, e);
        }
    }

    private boolean isEmpty(Path folder) {
        try (Stream<Path> contents = Files.list(folder)) {
            return contents.findAny().isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    private void deleteFolder(Path folder) {
        try {
            Files.delete(folder);
            log.info("Deleted: {}", folder);
        } catch (Exception e) {
            log.error("Failed to delete: {}", folder, e);
        }
    }

}
