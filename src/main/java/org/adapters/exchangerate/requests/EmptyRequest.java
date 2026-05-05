package org.adapters.exchangerate.requests;

import org.core.enums.HttpMethodEnum;
import org.core.holders.RequestHolder;
import org.core.interfaces.IApiRequestValue;

import java.net.URI;

public class EmptyRequest implements IApiRequestValue {
    private final String url;

    public EmptyRequest(String url) {
        this.url = url;
    }

    @Override
    public RequestHolder convert() {
        return new RequestHolder.Builder(URI.create(url), HttpMethodEnum.GET).build();
    }
}