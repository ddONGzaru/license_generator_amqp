package io.manasobi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class AppRunner {

    public static void main(String[] args) {
    	
    	SpringApplication app = new SpringApplication(AppRunner.class);
    	app.run(args);
    }
    
}
