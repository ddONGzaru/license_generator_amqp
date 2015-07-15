/**
 * 
 */
package io.manasobi.commons.config;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lab
 *
 */
public class AppArgsConfig {

	private Map<String, String> argsConfigMap = new HashMap<String, String>();
	
	private String[] configFilenames = {
		"application",
		"license"
	};
	
	
	public AppArgsConfig(String... args) {
		
		build(args);
	}
	
	private void build(String... args) {
		
		for (String arg : args) {
			
			/*if (!arg.contains(":")) {
				continue;
			}*/
			
			String[] argUnitArray = arg.split("=");
			
			argsConfigMap.put(argUnitArray[0].trim(), argUnitArray[1].trim());
		}
		
		
		if (!argsConfigMap.containsKey("configScan")) {
			argsConfigMap.put("configScan", "true");
		}
		
		if (!argsConfigMap.containsKey("profile")) {
			argsConfigMap.put("profile", "dev");
		}
		
		if (!argsConfigMap.containsKey("appConfigLocation")) {
			argsConfigMap.put("appConfigLocation", "classpath:config/");
			//argsConfigMap.put("appConfigLocation", "file:d:/config/");
		}
		
	}
	
	public String getAppConfigLocation() {
		return argsConfigMap.get("appConfigLocation");
	}
	
	public String getProfile() {
		return argsConfigMap.get("profile");
	}
	
	public boolean enableConfigScan() {
		return Boolean.valueOf(argsConfigMap.get("configScan"));
	}
	
	public String getAppConfigNames() {
		
		StringBuilder sb = new StringBuilder();
		
		for (String configFilename : configFilenames) {
			sb.append(configFilename + ",");
		}
		
		String appConfigFilenames = sb.toString();
		
		return appConfigFilenames.substring(0, appConfigFilenames.length() - 1);
		
	}
	
}
