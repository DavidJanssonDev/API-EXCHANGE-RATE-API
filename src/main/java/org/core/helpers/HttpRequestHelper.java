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

public class HttpRequestHelper {

    private static final HttpClient client = HttpClient.newHttpClient();

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

            String errorKey  = json.getString(adapter.getErrorField());
            IE error         = (IE) adapter.getErrorMap().get(errorKey);

            if (error == null)
                throw new RuntimeException("Unknown error type received: " + errorKey);

            return ResultHolder.error(error);

        } catch (Exception e) {
            throw new RuntimeException("Request failed: " + e.getMessage(), e);
        }
    }

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
