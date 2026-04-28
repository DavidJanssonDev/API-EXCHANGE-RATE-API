package org.httpApiClasses.ApiContext.RequestValues;

import org.httpApiClasses.ApiContext.ContextBodyObject;
import org.httpApiClasses.Interface.IApiRequestValue;

public class ComparisonRequest implements IApiRequestValue {
    protected final String from;
    protected String to;

    public ComparisonRequest(String from, String s) {
        this.from = from;
        this.to = s;
    }


    @Override
    public ContextBodyObject convert() {
        return ContextBodyObject.of("from", from, "to", to);
    }
}
