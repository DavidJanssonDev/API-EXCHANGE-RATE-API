package org.core.enums;

/**
 * Represents the HTTP verbs supported by the framework.
 *
 * <p>Used in {@link org.core.holders.RequestHolder} to specify which HTTP method
 * should be used when sending a request via
 * {@link org.core.helpers.HttpRequestBuilder#sendRequest}.</p>
 */
public enum HttpMethodEnum {

    /**
     * HTTP GET — retrieves a resource without modifying server state.
     *
     * <p>No request body is sent.</p>
     */
    GET,

    /**
     * HTTP POST — submits data to create a new resource.
     *
     * <p>Expects a request body.</p>
     */
    POST,

    /**
     * HTTP PUT — replaces an existing resource entirely.
     *
     * <p>Expects a request body containing the full resource representation.</p>
     */
    PUT,

    /**
     * HTTP DELETE — removes a resource.
     *
     * <p>No request body is sent.</p>
     */
    DELETE,

    /**
     * HTTP PATCH — partially updates an existing resource.
     *
     * <p>Expects a request body containing only the fields to be changed.
     * Note: Java's built-in {@link java.net.http.HttpClient} has no dedicated
     * {@code .patch()} method; {@link org.core.helpers.HttpRequestBuilder}
     * handles this via {@code .method("PATCH", body)}.</p>
     */
    PATCH
}
