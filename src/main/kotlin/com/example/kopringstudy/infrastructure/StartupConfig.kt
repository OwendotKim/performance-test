package com.example.kopringstudy.infrastructure

import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.nio.file.Files
import java.nio.file.Path

@Configuration
class StartupConfig {
    private val logger = LoggerFactory.getLogger(StartupConfig::class.java)

    @Bean
    fun cleanupUploadsFolder(): ApplicationRunner {
        return ApplicationRunner {
            val uploadsPath = Path.of("uploads")
            try {
                if (Files.exists(uploadsPath)) {
                    Files.walk(uploadsPath)
                        .sorted(Comparator.reverseOrder()) // 하위 디렉터리부터 삭제
                        .forEach(Files::delete)
                    logger.info("Successfully deleted 'uploads' folder.")
                } else {
                    logger.info("'uploads' folder does not exist. No cleanup needed.")
                }
            } catch (e: Exception) {
                logger.error("Failed to delete 'uploads' folder: ${e.message}", e)
            }
        }
    }
}
