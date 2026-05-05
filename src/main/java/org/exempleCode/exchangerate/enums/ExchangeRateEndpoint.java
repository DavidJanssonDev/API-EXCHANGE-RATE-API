package org.exempleCode.exchangerate.enums;

import org.exempleCode.exchangerate.results.*;
import org.core.interfaces.IReturnType;

public enum ExchangeRateEndpoint {
    STANDARD("latest/CURRENCY",                   StandardApiResult.class),
    COMPARISON("pair/CURRENCY/CURRENCY",           PairApiResult.class),
    COMPARISON_AMOUNT("pair/CURRENCY/CURRENCY/AMOUNT", PairAmountApiResult.class),
    SUPPORTED_CURRENCY_CODE("codes",               CurrencyCodeApiResult.class),
    QUOTA("quota",                                 QuotaApiResult.class);

    private final String path;
    private final Class<? extends IReturnType>  returnType;

    ExchangeRateEndpoint(String path, Class<? extends IReturnType> returnType) {
        this.path       = path;
        this.returnType = returnType;
    }

    public String getPath()                              { return path; }
    public Class<? extends IReturnType> getReturnType()  { return returnType; }
}
