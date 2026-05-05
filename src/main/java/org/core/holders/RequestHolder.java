package org.core.holders;

import org.core.enums.HttpMethodEnum;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


/**
 * Immutable value object that encapsulates everything needed to fire a single
 * HTTP request.
 *
 * <p>{@code RequestHolder} is produced by {@link org.core.interfaces.IApiRequestValue#convert()}
 * and consumed by {@link org.core.helpers.HttpRequestBuilder#sendRequest}. Because it is
 * immutable, it is safe to inspect, log, or pass between threads without copying.</p>
 *
 * <p>Instances are created exclusively through the nested {@link Builder}:</p>
 * <pre>{@code
 * RequestHolder holder = new RequestHolder.Builder(uri, HttpMethod.GET)
 *     .header("Accept", "application/json")
 *     .build();
 * }</pre>
 */
public class RequestHolder {
    private final URI uri;
    private final HttpMethodEnum method;
    private final Map<String, String> headers;
    private final String body;

    /** Private — use {@link Builder}. */
    private RequestHolder(Builder builder) {
        this.uri     = builder.uri;
        this.method  = builder.method;
        this.headers = builder.headers;
        this.body    = builder.body;
    }

    /**
     * Returns the fully-resolved endpoint URI, including any path segments,
     * query parameters, or embedded API keys.
     *
     * @return non-null URI
     */
    public URI getUri()                        { return uri; }

    /**
     * Returns the HTTP verb for this request.
     *
     * @return non-null {@link HttpMethodEnum}
     */
    public HttpMethodEnum getMethod()              { return method; }

    /**
     * Returns the HTTP headers to be sent with the request.
     *
     * @return non-null, possibly empty, unmodifiable-in-practice map
     */
    public Map<String, String> getHeaders()    { return headers; }

    /**
     * Returns the request body, if any.
     *
     * <p>Present for {@code POST}, {@code PUT}, and {@code PATCH} requests
     * that require a body; absent for {@code GET} and {@code DELETE}.</p>
     *
     * @return {@link Optional} wrapping the body string, or empty
     */
    public Optional<String> getBody()          { return Optional.ofNullable(body); }


    // ----------------------------------------------------------------
    //  Builder
    // ----------------------------------------------------------------


    /**
     * Fluent builder for {@link RequestHolder}.
     *
     * <p>Only URI and HTTP method are mandatory; headers and body are optional.
     * Add as many headers as needed via repeated {@link #header(String, String)}
     * calls before calling {@link #build()}.</p>
     */
    public static class Builder {
        private final URI uri;
        private final HttpMethodEnum method;
        private final Map<String, String> headers = new HashMap<>();
        private String body = null;

        /**
         * Creates a builder with the two mandatory fields.
         *
         * @param uri    fully-resolved target URI; must not be {@code null}
         * @param method HTTP verb; must not be {@code null}
         */
        public Builder(URI uri, HttpMethodEnum method) {
            this.uri    = uri;
            this.method = method;
        }

        /**
         * Adds a single HTTP header.
         *
         * <p>Calling this method multiple times with the same key overwrites
         * the previous value (last-write wins).</p>
         *
         * @param key   header name, e.g. {@code "Content-Type"}
         * @param value header value, e.g. {@code "application/json"}
         * @return this builder, for chaining
         */
        public Builder header(String key, String value) {
            this.headers.put(key, value);
            return this;
        }

        /**
         * Sets the request body.
         *
         * <p>Should only be used for methods that accept a body
         * ({@code POST}, {@code PUT}, {@code PATCH}). Has no effect on
         * the actual HTTP send for {@code GET} or {@code DELETE} — the
         * {@link org.core.helpers.HttpRequestBuilder} ignores it in those cases.</p>
         *
         * @param body raw body string, e.g. a JSON payload
         * @return this builder, for chaining
         */
        public Builder body(String body) {
            this.body = body;
            return this;
        }

        /**
         * Constructs the immutable {@link RequestHolder}.
         *
         * @return new {@link RequestHolder} with the values set on this builder
         */
        public RequestHolder build() {
            return new RequestHolder(this);
        }

    }
}
