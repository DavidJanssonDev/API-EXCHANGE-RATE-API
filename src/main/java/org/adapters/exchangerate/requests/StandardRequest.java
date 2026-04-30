package org.adapters.exchangerate.requests;

import org.core.enums.HttpMethod;
import org.core.holders.RequestHolder;
import org.core.interfaces.IApiRequestValue;

import java.net.URI;

public class StandardRequest implements IApiRequestValue {
    private final String baseUrl;
    private final String apiKey;
    private final String baseCurrency;

    public StandardRequest(String baseUrl, String apiKey, String baseCurrency) {
        this.baseUrl      = baseUrl;
        this.apiKey       = apiKey;
        this.baseCurrency = baseCurrency;
    }

    @Override
    public RequestHolder convert() {
        URI uri = URI.create(baseUrl + apiKey + "/latest/" + baseCurrency);
        return new RequestHolder.Builder(uri, HttpMethod.GET).build();
    }

}
