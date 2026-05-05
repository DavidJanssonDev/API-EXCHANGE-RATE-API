package org.core.interfaces;

import org.core.holders.RequestHolder;

/**
 * Represents a single, fully-specified API request.
 *
 * <p>Implementing classes correspond to one logical operation of a particular API
 * (e.g. "fetch latest rates for a base currency", "convert an amount between two
 * currencies"). They are responsible for building the correct endpoint URI,
 * adding any required headers, and setting the HTTP method.</p>
 *
 * <p>The {@link #convert()} method packages all of that information into a
 * {@link RequestHolder} that the HTTP layer can consume without knowing anything
 * about the specific API or operation.</p>
 *
 * <p>Usage pattern:</p>
 * <pre>{@code
 * IApiRequestValue request = adapter.standard("USD");
 * RequestHolder holder     = request.convert();
 * HttpRequestBuilder.sendRequest(holder, StandardApiResult.class, adapter);
 * }</pre>
 */
public interface IApiRequestValue {

    /**
     * Converts this request specification into a {@link RequestHolder}.
     *
     * <p>Implementations must resolve the full URI (including base URL, path
     * segments, and any query parameters), choose the correct HTTP method,
     * and attach all required headers before calling {@link RequestHolder.Builder#build()}.</p>
     *
     * @return a non-null {@link RequestHolder} ready to be sent
     */
    RequestHolder convert();
}
