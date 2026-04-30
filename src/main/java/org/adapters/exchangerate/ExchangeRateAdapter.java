package org.adapters.exchangerate;

import org.adapters.exchangerate.errors.*;
import org.adapters.exchangerate.parsers.ExchangeRateParser;
import org.adapters.exchangerate.requests.*;
import org.core.enums.ApiKeyStyle;
import org.core.interfaces.IApiAdapter;
import org.core.interfaces.IRequestError;
import org.core.interfaces.IReturnType;
import org.core.helpers.FileReaderHelper;
import org.json.JSONObject;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

public class ExchangeRateAdapter implements IApiAdapter<IReturnType, IRequestError> {

    private final URI baseUri;
    private final String apiKey;
    private final Map<String, IRequestError> errorMap;

//<----- CONSTRUCTOR ----->

    private ExchangeRateAdapter(String apiKey, String baseUrl) {
        this.apiKey   = apiKey;
        this.baseUri  = URI.create(baseUrl);
        this.errorMap = new HashMap<>();
        errorMap.put("invalid-key",      new InvalidKeyError());
        errorMap.put("inactive-account", new InactiveAccountError());
        errorMap.put("quota-reached",    new QuotaReachedError());
    }

    public static ExchangeRateAdapter fromFile(String fileName) {
        var values = FileReaderHelper.readFromFile(fileName);
        return new ExchangeRateAdapter(
                values.get("api_key"),
                values.get("base_api_link")
        );
    }

    public static ExchangeRateAdapter fromApiKey(String apiKey, String baseUrl) {
        return new ExchangeRateAdapter(apiKey, baseUrl);
    }

//<----- REQUEST BUILDERS ----->

    public StandardRequest standard(String baseCurrency) {
        return new StandardRequest(baseUri.toString(), apiKey, baseCurrency);
    }

    public ComparisonRequest comparison(String from, String to) {
        return new ComparisonRequest(baseUri.toString(), apiKey, from, to);
    }

    public ComparisonAmountRequest comparisonAmount(String from, String to, int amount) {
        return new ComparisonAmountRequest(baseUri.toString(), apiKey, from, to, amount);
    }

    public EmptyRequest codes() {
        return new EmptyRequest(baseUri.toString() + apiKey + "/codes");
    }

    public EmptyRequest quota() {
        return new EmptyRequest(baseUri.toString() + apiKey + "/quota");
    }

//<----- IADAPTER IMPLEMENTATION ----->

    @Override
    public URI getBaseUri() { return baseUri; }

    @Override
    public Optional<String> getApiKey() { return Optional.of(apiKey); }

    @Override
    public ApiKeyStyle getKeyStyle() { return ApiKeyStyle.PATH; }

    @Override
    public Predicate<JSONObject> getSuccessCondition() {
        return json -> json.getString("result").equals("success");
    }

    @Override
    public String getErrorField() { return "error-type"; }

    @Override
    public Map<String, IRequestError> getErrorMap() { return errorMap; }

    @Override
    public IReturnType parseSuccess(String json, Class<IReturnType> returnType) {
        return ExchangeRateParser.parse(json, returnType);
    }
}