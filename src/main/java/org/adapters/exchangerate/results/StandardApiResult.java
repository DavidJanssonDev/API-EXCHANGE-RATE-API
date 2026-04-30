package org.adapters.exchangerate.results;

import org.core.interfaces.IReturnType;

import java.util.Map;

public class StandardApiResult implements IReturnType {
    private final String result;
    private final String baseCode;
    private final String timeLastUpdateUtc;
    private final String timeNextUpdateUtc;
    private final Map<String, Double> conversionRates;


    public StandardApiResult(String result, String baseCode,
                             String timeLastUpdateUtc, String timeNextUpdateUtc,
                             Map<String, Double> conversionRates) {
        this.result             = result;
        this.baseCode           = baseCode;
        this.timeLastUpdateUtc  = timeLastUpdateUtc;
        this.timeNextUpdateUtc  = timeNextUpdateUtc;
        this.conversionRates    = conversionRates;
    }

    public Double getRate(String code)              { return conversionRates.get(code); }
    public String getResult()                       { return result; }
    public String getBaseCode()                     { return baseCode; }
    public String getTimeLastUpdateUtc()            { return timeLastUpdateUtc; }
    public String getTimeNextUpdateUtc()            { return timeNextUpdateUtc; }
    public Map<String, Double> getConversionRates() { return conversionRates; }

    @Override
    public String toString() {
        return "StandardApiResult{baseCode='" + baseCode + "', rates=" + conversionRates + "}";
    }

}
