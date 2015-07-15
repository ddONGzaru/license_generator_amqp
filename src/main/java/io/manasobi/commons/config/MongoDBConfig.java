package io.manasobi.commons.config;

import java.net.UnknownHostException;

import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

@Configuration
public class MongoDBConfig {

	@Bean
    public Jongo jongo() {
		
        DB db;
        
        try {
            db = new MongoClient("127.0.0.1", 27017).getDB("manasobi");
        } catch (UnknownHostException e) {
            throw new MongoException("Connection error : ", e);
        }
        
        return new Jongo(db);
    }

    @Bean
    public MongoCollection licenseDetailsRepo() {
    	return jongo().getCollection("licenseDetails");
    }
    
    
}
