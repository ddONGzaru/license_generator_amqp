package io.manasobi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.config.ConfigFileApplicationListener;

import io.manasobi.commons.config.AppArgsConfig;


@SpringBootApplication
public class AppRunner {

    public static void main(String[] args) {
    	
    	SpringApplication app = new SpringApplication(AppRunner.class);
    	
    	app.addListeners(getConfigFileApplicationListener(args));
    	app.run(args);
    	
    	
    	/*ApplicationContext context = app.run(args);
    	 * 
    	
    	String[] profiles = context.getEnvironment().getActiveProfiles();
    	
    	
    	for (String profile : profiles) {
    		System.out.println(profile);
    		
    	}*/
    	
    }
    
    private static ConfigFileApplicationListener getConfigFileApplicationListener(String[] args) {
    	
    	AppArgsConfig argsConfig = new AppArgsConfig(args);
    	
    	ConfigFileApplicationListener listener = new ConfigFileApplicationListener();
    	
    	listener.setSearchLocations(argsConfig.getAppConfigLocation());
    	listener.setSearchNames(argsConfig.getAppConfigNames());
    	
    	return listener;    	
    }
    
}
