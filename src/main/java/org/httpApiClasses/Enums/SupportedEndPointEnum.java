package org.httpApiClasses.Enums;

import org.httpApiClasses.Interface.IReturnType;
import org.httpApiClasses.RetrunClasses.*;

public enum SupportedEndPointEnum {
    STANDARD("latest/CURRENCY", StandardApiResult.class),
    COMPARISON("pair/CURRENCY/CURRENCY", PairApiResult.class),
    COMPARISON_AMOUNT("pair/CURRENCY/CURRENCY/AMOUNT", PairAmountApiResult.class),
    SUPPORTED_CURRENCY_CODE("codes", CurrencyCodeApiResult.class),
    QUOTA("quota", QuotaApiResult.class);

    private final String value;
    private final Class<? extends IReturnType> returnType;



    SupportedEndPointEnum(String value, Class<? extends IReturnType> returnType) {
        this.value = value;
        this.returnType = returnType;
    }

    public String getValue() { return value; }
    public Class<? extends IReturnType> getReturnType() { return returnType; }
}
