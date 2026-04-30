package org.adapters.exchangerate.requests;

import org.core.enums.HttpMethod;
import org.core.holders.RequestHolder;
import org.core.interfaces.IApiRequestValue;

import java.net.URI;

public class ComparisonAmountRequest implements IApiRequestValue {
    private final String baseUrl;
    private final String apiKey;
    private final String from;
    private final String to;
    private final int amount;

    public ComparisonAmountRequest(String baseUrl, String apiKey,
                                   String from, String to, int amount) {
        this.baseUrl = baseUrl;
        this.apiKey  = apiKey;
        this.from    = from;
        this.to      = to;
        this.amount  = amount;
    }

    @Override
    public RequestHolder convert() {
        URI uri = URI.create(baseUrl + apiKey + "/pair/" + from + "/" + to + "/" + amount);
        return new RequestHolder.Builder(uri, HttpMethod.GET).build();
    }
}