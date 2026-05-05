package org.core.interfaces;

import org.core.enums.ApiKeyStyleEnum;
import org.json.JSONObject;

import java.net.URI;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
/**
 * Central contract for any API adapter in the framework.
 *
 * <p>An adapter encapsulates everything that is specific to one external REST API:
 * its base URL, authentication strategy, success/error detection logic, and
 * the mapping from raw JSON to strongly-typed result objects.</p>
 *
 * <p>Generic parameters allow each adapter to be type-safe end-to-end:</p>
 * <ul>
 *   <li>{@code R} — the supertype of all successful response objects for this API.</li>
 *   <li>{@code IE} — the supertype of all error objects this API can return.</li>
 * </ul>
 *
 * <p>Implementing classes are typically constructed from a configuration file
 * (see {@link org.core.helpers.FileReaderHelper}) and passed to
 * {@link org.core.helpers.HttpRequestBuilder#sendRequest} together with a
 * {@link org.core.holders.RequestHolder}.</p>
 *
 * @param <R>  return-type marker; must extend {@link IReturnType}
 * @param <IE> error-type marker; must extend {@link IRequestError}
 */
public interface IApiAdapter<R extends IReturnType, IE extends IRequestError> {

    /**
     * Returns the root URI of the API.
     *
     * <p>Request builders append path segments (and optionally the API key)
     * to this URI when constructing the final endpoint URL.</p>
     *
     * @return non-null base URI, e.g. {@code https://v6.exchangerate-api.com/v6}
     */
    URI getBaseUri();

    /**
     * Returns the API key, if one is required by this service.
     *
     * @return {@link Optional} containing the key string, or {@link Optional#empty()}
     *         when the API does not require authentication.
     */
    Optional<String> getApiKey();

    /**
     * Describes how the API key should be attached to outgoing requests.
     *
     * <p>The value controls URL construction inside the request-value objects:
     * <ul>
     *   <li>{@link ApiKeyStyleEnum#PATH}  — key is a path segment:
     *       {@code /v6/{API_KEY}/endpoint}</li>
     *   <li>{@link ApiKeyStyleEnum#QUERY} — key is a query parameter:
     *       {@code /endpoint?apikey={API_KEY}}</li>
     *   <li>{@link ApiKeyStyleEnum#NONE}  — no key is added.</li>
     * </ul>
     *
     * @return the key injection strategy; never {@code null}
     */
    ApiKeyStyleEnum getKeyStyle();

    /**
     * Predicate that determines whether a JSON response body represents success.
     *
     * <p>The predicate is evaluated by {@link org.core.helpers.HttpRequestBuilder}
     * immediately after the response is parsed. A {@code true} result causes the
     * success branch to execute; {@code false} causes error lookup.</p>
     *
     * <p>Example implementation for ExchangeRate-API:</p>
     * <pre>{@code
     * json -> "success".equals(json.optString("result"))
     * }</pre>
     *
     * @return a non-null {@link Predicate} over the parsed response JSON
     */
    Predicate<JSONObject> getSuccessCondition();

    /**
     * The JSON field name that contains the error code on a failed response.
     *
     * <p>When {@link #getSuccessCondition()} returns {@code false}, the HTTP
     * builder reads this field from the JSON body and uses its value as the key
     * into {@link #getErrorMap()}.</p>
     *
     * @return field name string, e.g. {@code "error-type"}
     */
    String getErrorField();

    /**
     * Maps error-code strings (as returned by the API) to typed error objects.
     *
     * <p>Each key must match a value that the API can place in the field returned
     * by {@link #getErrorField()}. If the received code is not present in the map,
     * {@link org.core.helpers.HttpRequestBuilder} throws a {@link RuntimeException}.</p>
     *
     * @return immutable or effectively-immutable map; never {@code null}
     */
    Map<String, IE> getErrorMap();

    /**
     * Deserialises a successful JSON response body into the expected return type.
     *
     * <p>Called only after {@link #getSuccessCondition()} has returned {@code true}.
     * The implementing class decides how to parse {@code json} — typically using
     * a JSON library or manual field extraction.</p>
     *
     * @param json       the raw response body string
     * @param returnType the {@link Class} object of the concrete result type to create
     * @return a non-null, populated result object
     */
    R parseSuccess(String json, Class<R> returnType);
}
