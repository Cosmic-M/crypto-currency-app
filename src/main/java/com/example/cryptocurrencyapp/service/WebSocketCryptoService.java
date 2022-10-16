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
    @Value(value = "${priceWebSocketApiLink}")
    private String link;
    @Value(value = "${API_KEY}")
    private String key;

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
            final WebsocketClientEndpoint clientEndPoint = new WebsocketClientEndpoint(new URI(link));
            clientEndPoint.addMessageHandler(messageHandler::handleMessage);
            clientEndPoint.sendMessage(json);
            Thread.sleep(3500);
        } catch (InterruptedException ex) {
            throw new RuntimeException("InterruptedException exception: " + ex.getMessage());
        } catch (URISyntaxException ex) {
            throw new RuntimeException("URISyntaxException exception: " + ex.getMessage());
        }
    }
}
