package com.chat.app.service.converter;

import org.bson.BsonTimestamp;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
public class LocalDateTimeToBsonConverter implements Converter<LocalDateTime, BsonTimestamp> {

    @Override
    public BsonTimestamp convert(LocalDateTime source) {
        long seconds = source.toEpochSecond(ZoneOffset.UTC);
        return new BsonTimestamp((int) seconds, 0);
    }
}

