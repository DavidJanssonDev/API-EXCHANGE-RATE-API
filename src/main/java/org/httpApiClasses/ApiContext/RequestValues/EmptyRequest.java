package org.httpApiClasses.ApiContext.RequestValues;

import org.httpApiClasses.ApiContext.ContextBodyObject;
import org.httpApiClasses.Interface.IApiRequestValue;

public class EmptyRequest implements IApiRequestValue {
    @Override
    public ContextBodyObject convert() {
        return ContextBodyObject.of();
    }
}
