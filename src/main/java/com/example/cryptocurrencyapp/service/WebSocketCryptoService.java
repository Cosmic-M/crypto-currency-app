package com.example.cryptocurrencyapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WebSocketCryptoService {
    @Value(value = "${priceWebSocketApiLink}")
    private String link;
    @Value(value = "${API_KEY}")
    private String key;

    public void sendRequest(String trader, List<String> cryptoGroup) {
        StringBuilder builder = new StringBuilder();
        boolean firstLine = true;
        for (String crypto : cryptoGroup) {
            if (!firstLine) {
                builder.append(",");
            }
            builder.append(trader)
                    .append("_")
                    .append("PERP")
                    .append("_")
                    .append(crypto)
                    .append("_")
                    .append("USD");
            firstLine = false;
        }
        String json = "{\"type\":\"hello\","
                + "\"apikey\":\"" + key + "\","
                + "\"heartbeat\":false,"
                + "\"subscribe_data_type\":[\"trade\"],"
                + "\"subscribe_filter_asset_id\": [\""
                + builder
                + "\"]}";
        try {
            // open websocket
            final WebsocketClientEndpoint clientEndPoint = new WebsocketClientEndpoint(new URI(link));
            // add listener
            clientEndPoint.addMessageHandler(System.out::println);
            // send message to websocket
            clientEndPoint.sendMessage(json);
            // wait 5 seconds for messages from websocket
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            throw new RuntimeException("InterruptedException exception: " + ex.getMessage());
        } catch (URISyntaxException ex) {
            throw new RuntimeException("URISyntaxException exception: " + ex.getMessage());
        }
    }
}
