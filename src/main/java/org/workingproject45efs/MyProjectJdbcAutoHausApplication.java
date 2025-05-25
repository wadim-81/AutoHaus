package org.workingproject45efs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.workingproject45efs")
public class MyProjectJdbcAutoHausApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyProjectJdbcAutoHausApplication.class, args);
    }
}
