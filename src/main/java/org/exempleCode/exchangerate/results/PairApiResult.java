package org.exempleCode.exchangerate.results;

import org.core.interfaces.IReturnType;

public class PairApiResult implements IReturnType {
    private final String baseCode;
    private final String targetCode;
    private final double conversionRate;

    public PairApiResult(String baseCode, String targetCode, double conversionRate) {
        this.baseCode       = baseCode;
        this.targetCode     = targetCode;
        this.conversionRate = conversionRate;
    }

    public String getBaseCode()       { return baseCode; }
    public String getTargetCode()     { return targetCode; }
    public double getConversionRate() { return conversionRate; }

    @Override
    public String toString() {
        return "PairApiResult{" + baseCode + " → " + targetCode + " = " + conversionRate + "}";
    }
}
