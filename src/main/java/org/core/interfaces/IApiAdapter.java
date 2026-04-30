package org.core.interfaces;

import org.core.enums.ApiKeyStyle;
import org.json.JSONObject;

import java.net.URI;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

public interface IApiAdapter<R extends IReturnType, IE extends IRequestError> {

    URI getBaseUri();
    Optional<String> getApiKey();
    ApiKeyStyle getKeyStyle();

    Predicate<JSONObject> getSuccessCondition();
    String getErrorField();
    Map<String, IE> getErrorMap();

    R parseSuccess(String json, Class<R> returnType);
}
