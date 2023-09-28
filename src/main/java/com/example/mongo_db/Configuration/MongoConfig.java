package com.example.mongo_db.Configuration;


import com.example.mongo_db.Converter.BinaryConverter;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import java.util.List;

@Configuration
@ComponentScan("com.example.mongo_db")
public class MongoConfig extends AbstractMongoClientConfiguration {
    @Override
    protected String getDatabaseName() {
        return "users";
    }

    @Override
    public MongoClient mongoClient() {
        return MongoClients.create("mongodb://localhost:27017");
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), getDatabaseName());
    }

    @Bean
    public MongoCustomConversions customConversions() {
        return new MongoCustomConversions(List.of(new BinaryConverter()));
    }

//    @Bean
//    public GridFsTemplate gridFsTemplate() throws Exception {
//        return new GridFsTemplate(mongoDbFactory(), mappingMongoConverter());
//    }
//
//    @Bean
//    public MappingMongoConverter mappingMongoConverter() {
//        DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory());
//        MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, new MongoMappingContext());
//        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
//        return converter;
//    }
}
