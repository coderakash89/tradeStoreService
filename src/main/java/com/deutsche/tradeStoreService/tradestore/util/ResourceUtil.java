package com.deutsche.tradeStoreService.tradestore.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ResourceUtil {
    public static<T> T readResourceContents(String filename, TypeReference<T> typeReference) {
        try(InputStream resource = ResourceUtil.class.getResourceAsStream(filename)) {
            if(resource == null) {
                throw new FileNotFoundException(filename);
            }

            if(typeReference.getType().getTypeName().equals("java.lang.String")) {
                return (T) IOUtils.toString(resource, "UTF-8");
            }

            return new ObjectMapper().readValue(resource, typeReference);
        } catch (IOException e) {
           throw new RuntimeException(e);
        }
    }
}
