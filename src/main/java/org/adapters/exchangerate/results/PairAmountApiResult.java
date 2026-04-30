package org.adapters.exchangerate.results;

import org.core.interfaces.IReturnType;

public class PairAmountApiResult implements IReturnType {
    private final String baseCode;
    private final String targetCode;
    private final double conversionRate;
    private final double conversionResult;

    public PairAmountApiResult(String baseCode, String targetCode,
                               double conversionRate, double conversionResult) {
        this.baseCode         = baseCode;
        this.targetCode       = targetCode;
        this.conversionRate   = conversionRate;
        this.conversionResult = conversionResult;
    }

    public String getBaseCode()        { return baseCode; }
    public String getTargetCode()      { return targetCode; }
    public double getConversionRate()  { return conversionRate; }
    public double getConversionResult(){ return conversionResult; }

    @Override
    public String toString() {
        return "PairAmountApiResult{" + baseCode + " → " + targetCode +
                " rate=" + conversionRate + " result=" + conversionResult + "}";
    }
}
