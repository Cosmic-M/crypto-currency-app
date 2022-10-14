package com.example.cryptocurrencyapp.service;

import com.squareup.okhttp.Response;

public interface ResponseParser {
    <T> T parse(Response response, Class<T> clazz);
}
