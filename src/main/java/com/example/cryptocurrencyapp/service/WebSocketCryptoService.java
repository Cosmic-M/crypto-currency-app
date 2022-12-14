package com.example.cryptocurrencyapp.service;

import com.example.cryptocurrencyapp.service.impl.MessageHandlerImpl;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebSocketCryptoService {
    private final MessageHandlerImpl messageHandler;
    private WebsocketClientEndpoint clientEndPoint;
    @Value(value = "${priceWebSocketApiLink}")
    private String link;
    @Value(value = "${API_KEY}")
    private String key;
    private boolean isOpen = false;

    public void sendRequest(List<String> cryptoGroup) {
        StringBuilder builder = new StringBuilder();
        boolean firstLine = true;
        for (String crypto : cryptoGroup) {
            if (!firstLine) {
                builder.append(",");
            }
            builder.append("\"")
                    .append("COINBASE")
                    .append("_")
                    .append("SPOT")
                    .append("_")
                    .append(crypto)
                    .append("_")
                    .append("USD")
                    .append("\"");
            firstLine = false;
        }
        String json = "{\"type\":\"hello\","
                + "\"apikey\":\"" + key + "\","
                + "\"heartbeat\":false,"
                + "\"subscribe_data_type\":[\"trade\"],"
                + "\"subscribe_filter_symbol_id\": ["
                + builder
                + "]}";
        try {
            if (!isOpen) {
                clientEndPoint = new WebsocketClientEndpoint(new URI(link));
                isOpen = true;
            }
            clientEndPoint.addMessageHandler(messageHandler::handleMessage);
            clientEndPoint.sendMessage(json);
        } catch (URISyntaxException e) {
            throw new RuntimeException("URISyntaxException exception: " + e);
        }
    }
}
