package com.te.ems;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.te.ems.entity.Technology;
import com.te.ems.repository.TechnologyRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@SpringBootApplication
public class EmployeeManagementSystemApplication {

    private final TechnologyRepository technologyRepository;

    public static void main(String[] args) {
        SpringApplication.run(EmployeeManagementSystemApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            if (!technologyRepository.existsById("Python")) {
                technologyRepository.save(Technology.builder().technologyName("Java").build());
                technologyRepository.save(Technology.builder().technologyName("Python").build());
                technologyRepository.save(Technology.builder().technologyName("Javascript").build());
                technologyRepository.save(Technology.builder().technologyName("C#").build());
            }
        };
    }
}
