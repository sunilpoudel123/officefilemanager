package com.sdev.officefilemanager;

import com.sdev.officefilemanager.service.DocumentStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OfficefilemanagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OfficefilemanagerApplication.class, args);
    }

    @Bean
    CommandLineRunner init(DocumentStorageService storageService) {
        return (args) -> {
            storageService.init();
        };
    }

}

