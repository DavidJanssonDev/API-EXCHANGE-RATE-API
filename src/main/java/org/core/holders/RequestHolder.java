package org.core.holders;

import org.core.enums.HttpMethod;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class RequestHolder {
    private final URI uri;
    private final HttpMethod method;
    private final Map<String, String> headers;
    private final String body;

    private RequestHolder(Builder builder) {
        this.uri     = builder.uri;
        this.method  = builder.method;
        this.headers = builder.headers;
        this.body    = builder.body;
    }

    public URI getUri()                        { return uri; }
    public HttpMethod getMethod()              { return method; }
    public Map<String, String> getHeaders()    { return headers; }
    public Optional<String> getBody()          { return Optional.ofNullable(body); }

    //Builder so you only set what you need
    public static class Builder {
        private URI uri;
        private HttpMethod method;
        private Map<String, String> headers = new HashMap<>();
        private String body = null;


        public Builder(URI uri, HttpMethod method) {
            this.uri    = uri;
            this.method = method;
        }

        public Builder header(String key, String value) {
            this.headers.put(key, value);
            return this;
        }

        public Builder body(String body) {
            this.body = body;
            return this;
        }

        public RequestHolder build() {
            return new RequestHolder(this);
        }

    }
}
