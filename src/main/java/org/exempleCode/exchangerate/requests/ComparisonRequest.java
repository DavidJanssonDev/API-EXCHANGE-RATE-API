package org.exempleCode.exchangerate.requests;

import org.core.enums.HttpMethodEnum;
import org.core.holders.RequestHolder;
import org.core.interfaces.IApiRequestValue;

import java.net.URI;

public class ComparisonRequest implements IApiRequestValue {
    private final String baseUrl;
    private final String apiKey;
    private final String from;
    private final String to;

    public ComparisonRequest(String baseUrl, String apiKey, String from, String to) {
        this.baseUrl = baseUrl;
        this.apiKey  = apiKey;
        this.from    = from;
        this.to      = to;
    }

    @Override
    public RequestHolder convert() {
        URI uri = URI.create(baseUrl + apiKey + "/pair/" + from + "/" + to);
        return new RequestHolder.Builder(uri, HttpMethodEnum.GET).build();
    }
}