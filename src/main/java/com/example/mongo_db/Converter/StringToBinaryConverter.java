package com.example.mongo_db.Converter;


import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.core.convert.converter.Converter;

import java.util.Base64;

public class StringToBinaryConverter implements Converter<String, Binary> {

    @Override
    public Binary convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }

        byte[] bytes = Base64.getDecoder().decode(source);
        return new Binary(BsonBinarySubType.BINARY, bytes);
    }
}