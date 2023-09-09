package com.example.mongo_db.Converter;

import org.bson.types.Binary;
import org.springframework.core.convert.converter.Converter;

public class BinaryConverter implements Converter<Binary, String> {
    @Override
    public String convert(Binary binary) {
        if (binary != null) {
            byte[] data = binary.getData();
            if (data != null) {
                return new String(data);
            }
        }
        return null;
    }


}
