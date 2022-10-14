package com.example.cryptocurrencyapp.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class ResponseParserImpl implements ResponseParser {
    private final ObjectMapper objectMapper = new ObjectMapper();

    {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public <T> T parse(Response response, Class<T> clazz) {
        try {
            InputStream stream = response.body().byteStream();
            StringBuilder builder = new StringBuilder();
            for (int ch; (ch = stream.read()) != -1; ) {
                if (ch != 10 && ch != 32) {
                    builder.append((char) ch);
                }
            }
            return objectMapper.readValue(builder.toString(), clazz);
        } catch (IOException exc) {
            throw new RuntimeException("Cannot obtain stream from response object");
        }
    }
}
