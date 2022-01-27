package de.uniba.dsg.carrental.apigateway;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.MalformedURLException;
import java.net.URL;

@Component
public class HateoasModifierFilter implements GlobalFilter, Ordered {

    @Value("${server_port}")
    private int port;

    @Override
    public int getOrder() {
        return -2; // -1 is response write filter, must be called before that
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpResponse originalResponse = exchange.getResponse();
        DataBufferFactory bufferFactory = originalResponse.bufferFactory();
        ServerHttpResponseDecorator modifiedResponse = new ServerHttpResponseDecorator(originalResponse) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                if (body instanceof Flux) {
                    Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
                    return super.writeWith(fluxBody.map(dataBuffer -> {
                        byte[] content = new byte[dataBuffer.readableByteCount()];
                        dataBuffer.read(content);
                        try {
                            byte[] modifiedContent = modifyHateoas(new JSONObject(new String(content))).getBytes();
                            return bufferFactory.wrap(modifiedContent);
                        } catch (JSONException ex) {
                            return bufferFactory.wrap(content);
                        }
                    }));
                }
                return super.writeWith(body);
            }
        };

        return chain.filter(exchange.mutate().response(modifiedResponse).build());
    }

    private String modifyHateoas(JSONObject responseJson) {

        handleJSONObject(responseJson);

        return responseJson.toString();
    }

    private void handleJSONObject(JSONObject jsonObject) {
        jsonObject.keys().forEachRemaining(key -> {
            Object value = jsonObject.get(key);

            if (key.equals("_links") && value instanceof JSONObject) {
                handleLinkObject((JSONObject)value);
            } else if (value instanceof JSONObject) {
                handleJSONObject((JSONObject)value);
            } else if (value instanceof JSONArray) {
                handleJSONArray((JSONArray)value);
            }
        });
    }

    private void handleJSONArray(JSONArray jsonArray) {
        jsonArray.iterator().forEachRemaining(element -> {
            if (element instanceof JSONObject) {
                handleJSONObject((JSONObject)element);
            } else if (element instanceof JSONArray) {
                handleJSONArray((JSONArray)element);
            }
        });
    }

    private void handleLinkObject(JSONObject jsonObject) {
        jsonObject.keys().forEachRemaining(key -> {
            Object value = jsonObject.get(key);

            if (key.equals("href") && !(value instanceof JSONObject) && !(value instanceof JSONArray)) {
                try {
                    jsonObject.put(key, updateHateoasLink(new URL((String)value)));
                } catch (MalformedURLException ignored) { }
            } else if (value instanceof JSONObject) {
                handleLinkObject((JSONObject)value);
            }
        });
    }

    private URL updateHateoasLink(URL originalLink) throws MalformedURLException {
        return new URL(originalLink.getProtocol(), originalLink.getHost(), port, originalLink.getFile());
    }
}
