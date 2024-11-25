package com.chat.app.service.converter;

import org.bson.BsonTimestamp;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
public class BsonToLocalDateTimeConverter implements Converter<BsonTimestamp, LocalDateTime> {
    @Override

    public LocalDateTime convert(BsonTimestamp source) {
        return LocalDateTime.ofEpochSecond(source.getTime(), 0, ZoneOffset.UTC);
    }
}
