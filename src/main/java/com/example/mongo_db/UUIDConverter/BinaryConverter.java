package com.example.mongo_db.UUIDConverter;

import org.bson.types.Binary;
import org.springframework.core.convert.converter.Converter;

import java.nio.ByteBuffer;
import java.util.UUID;

public class BinaryConverter implements Converter<Binary, UUID> {
    @Override
    public UUID convert(Binary source) {
        byte[] bytes = source.getData();
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        long mostSignificantBits = byteBuffer.getLong();
        long leastSignificantBits = byteBuffer.getLong();
        return new UUID(mostSignificantBits, leastSignificantBits);
    }
}
