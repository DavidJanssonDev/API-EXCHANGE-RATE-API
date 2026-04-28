package org.httpApiClasses.ApiContext.RequestValues;

import org.httpApiClasses.ApiContext.ContextBodyObject;
import org.httpApiClasses.Interface.IApiRequestValue;

public class ComparisonAmountRequest implements IApiRequestValue {
    protected final String from;
    protected final String to;
    protected final int amount;

    public ComparisonAmountRequest(String from, String s, int amount) {
        this.from = from;
        this.to = s;
        this.amount = amount;
    }

    @Override
    public ContextBodyObject convert() {
        return ContextBodyObject.of("from", from, "to", to, "amount", amount);
    }
}
