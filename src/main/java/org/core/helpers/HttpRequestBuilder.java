package org.core.helpers;

import org.core.holders.RequestHolder;
import org.core.holders.ResultHolder;
import org.core.interfaces.IApiAdapter;
import org.core.interfaces.IRequestError;
import org.core.interfaces.IReturnType;
import org.json.JSONObject;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Static utility that sends an HTTP request and maps the response to a
 * {@link ResultHolder}.
 *
 * <p>This class is the execution engine of the framework. It bridges the
 * generic, API-agnostic {@link RequestHolder} and {@link IApiAdapter} contracts
 * with Java's standard {@link HttpClient}.</p>
 *
 * <p>A single shared {@link HttpClient} instance is reused for all requests.
 * The client is stateless, thread-safe, and handles connection pooling
 * automatically.</p>
 */
public class HttpRequestBuilder {

    /** Shared HTTP client — created once, reused for every request. */
    private static final HttpClient client = HttpClient.newHttpClient();


    /**
     * Sends an HTTP request described by {@code requestHolder} and returns a
     * typed {@link ResultHolder}.
     *
     * <p>Execution flow:</p>
     * <ol>
     *   <li>Build a {@link HttpRequest} from the {@link RequestHolder}.</li>
     *   <li>Send it synchronously with the shared {@link HttpClient}.</li>
     *   <li>Parse the response body as JSON.</li>
     *   <li>Evaluate {@link IApiAdapter#getSuccessCondition()} against the JSON.
     *     <ul>
     *       <li><b>true</b> → delegate to {@link IApiAdapter#parseSuccess} and
     *           wrap the result in {@link ResultHolder#success(IReturnType)}.</li>
     *       <li><b>false</b> → read the error code from
     *           {@link IApiAdapter#getErrorField()}, look it up in
     *           {@link IApiAdapter#getErrorMap()}, and wrap in
     *           {@link ResultHolder#error(IRequestError)}.</li>
     *     </ul>
     *   </li>
     * </ol>
     *
     * @param requestHolder the fully-built request to send; must not be {@code null}
     * @param returnType    the {@link Class} of the expected result type,
     *                      passed through to {@link IApiAdapter#parseSuccess}
     * @param adapter       the adapter that knows how to interpret the response
     * @param <R>           success result type
     * @param <IE>          error type
     * @return a non-null {@link ResultHolder} containing either the result or
     *         a typed error
     * @throws RuntimeException if the HTTP call fails, JSON parsing fails, or
     *                          the error code is not found in the adapter's error map
     */
    @SuppressWarnings("unchecked")
    public static <R extends IReturnType, IE extends IRequestError> ResultHolder<R, IE> sendRequest(
            RequestHolder requestHolder,
            Class<R> returnType,
            IApiAdapter<? extends IReturnType, ? extends IRequestError> adapter
    ) {
        try {
            HttpRequest request = buildRequest(requestHolder);

            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            JSONObject json = new JSONObject(response.body());

            if (adapter.getSuccessCondition().test(json)) {
                R result = (R) adapter.parseSuccess(response.body(), (Class) returnType);
                return ResultHolder.success(result);
            }

            // Error path: read the error code and map to a typed error object
            String errorKey = json.getString(adapter.getErrorField());
            IE error        = (IE) adapter.getErrorMap().get(errorKey);

            if (error == null)
                throw new RuntimeException("Unknown error type received: " + errorKey);

            return ResultHolder.error(error);

        } catch (Exception e) {
            throw new RuntimeException("Request failed: " + e.getMessage(), e);
        }
    }

    /**
     * Translates a {@link RequestHolder} into a standard {@link HttpRequest}.
     *
     * <p>Headers from the holder are attached in iteration order. The HTTP verb
     * is mapped as follows:
     * <ul>
     *   <li>{@code GET}, {@code DELETE} — no body.</li>
     *   <li>{@code POST}, {@code PUT}, {@code PATCH} — body from
     *       {@link RequestHolder#getBody()}, or {@code noBody()} if absent.</li>
     * </ul>
     * {@code PATCH} is sent via {@link HttpRequest.Builder#method(String, HttpRequest.BodyPublisher)}
     * because the Java HTTP client has no dedicated {@code .PATCH()} method.</p>
     *
     * @param holder the request specification; must not be {@code null}
     * @return a ready-to-send {@link HttpRequest}
     */
    private static HttpRequest buildRequest(RequestHolder holder) {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(holder.getUri());

        holder.getHeaders().forEach(builder::header);

        switch (holder.getMethod()) {
            case GET    -> builder.GET();
            case DELETE -> builder.DELETE();
            case POST   -> builder.POST(
                    holder.getBody()
                            .map(HttpRequest.BodyPublishers::ofString)
                            .orElse(HttpRequest.BodyPublishers.noBody())
            );
            case PUT    -> builder.PUT(
                    holder.getBody()
                            .map(HttpRequest.BodyPublishers::ofString)
                            .orElse(HttpRequest.BodyPublishers.noBody())
            );
            case PATCH  -> builder.method("PATCH",
                    holder.getBody()
                            .map(HttpRequest.BodyPublishers::ofString)
                            .orElse(HttpRequest.BodyPublishers.noBody())
            );
        }

        return builder.build();
    }
}
