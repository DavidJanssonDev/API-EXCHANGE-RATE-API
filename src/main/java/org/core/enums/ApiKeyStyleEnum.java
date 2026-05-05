package org.core.enums;

/**
 * Describes how an API key should be injected into outgoing HTTP requests.
 *
 * <p>The value is read by {@link org.core.interfaces.IApiAdapter#getKeyStyle()}
 * and acted upon inside each {@link org.core.interfaces.IApiRequestValue}
 * implementation when constructing the final URI.</p>
 */
public enum ApiKeyStyleEnum {
    /**
     * The API key is a path segment in the URL.
     *
     * <p>Example: {@code https://v6.exchangerate-api.com/v6/{API_KEY}/latest/USD}</p>
     */
    PATH,

    /**
     * The API key is appended as a query parameter.
     *
     * <p>Example: {@code https://api.example.com/endpoint?apikey={API_KEY}}</p>
     */
    QUERY,

    /**
     * No API key is used. The endpoint is publicly accessible.
     *
     * <p>Example: {@code https://api.example.com/endpoint}</p>
     */
    NONE
}
