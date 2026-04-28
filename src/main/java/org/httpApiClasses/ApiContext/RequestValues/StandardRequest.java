package org.httpApiClasses.ApiContext.RequestValues;

import org.httpApiClasses.ApiContext.ContextBodyObject;
import org.httpApiClasses.Interface.IApiRequestValue;

public class StandardRequest implements IApiRequestValue {
    private final String baseCurrency;

    public StandardRequest(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    @Override
    public ContextBodyObject convert() {
        return ContextBodyObject.of("base", baseCurrency);
    }
}
