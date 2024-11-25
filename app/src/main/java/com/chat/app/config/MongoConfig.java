package com.chat.app.config;

import com.chat.app.service.converter.BsonToLocalDateTimeConverter;
import com.chat.app.service.converter.LocalDateTimeToBsonConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.Arrays;
@Configuration
public class MongoConfig {
    @Bean
    public MongoCustomConversions customConversions() {
        return new MongoCustomConversions(Arrays.asList(
                new BsonToLocalDateTimeConverter(),
                new LocalDateTimeToBsonConverter()
        ));
    }
}