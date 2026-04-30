package org.httpApiClasses.RetrunClasses;

import org.httpApiClasses.Interface.IReturnType;

import java.util.Map;

public class StandardApiResult implements IReturnType {
    private final String result;
    private final long timeLastUpdateUnix;
    private final String timeLastUpdateUtc;
    private final long timeNextUpdateUnix;
    private final String timeNextUpdateUtc;
    private final String baseCode;
    private final Map<String, Double> conversionRates;

    public StandardApiResult(String result, long timeLastUpdateUnix, String timeLastUpdateUtc,
                             long timeNextUpdateUnix, String timeNextUpdateUtc,
                             String baseCode, Map<String, Double> conversionRates) {
        this.result             = result;
        this.timeLastUpdateUnix = timeLastUpdateUnix;
        this.timeLastUpdateUtc  = timeLastUpdateUtc;
        this.timeNextUpdateUnix = timeNextUpdateUnix;
        this.timeNextUpdateUtc  = timeNextUpdateUtc;
        this.baseCode           = baseCode;
        this.conversionRates    = conversionRates;
    }

    // Get a specific rate e.g. getRate("SEK")
    public Double getRate(String currencyCode) {
        return conversionRates.get(currencyCode);
    }

    public String getResult()                        { return result; }
    public long getTimeLastUpdateUnix()              { return timeLastUpdateUnix; }
    public String getTimeLastUpdateUtc()             { return timeLastUpdateUtc; }
    public long getTimeNextUpdateUnix()              { return timeNextUpdateUnix; }
    public String getTimeNextUpdateUtc()             { return timeNextUpdateUtc; }
    public String getBaseCode()                      { return baseCode; }
    public Map<String, Double> getConversionRates()  { return conversionRates; }

    @Override
    public String toString() {
        return "StandardApiResult{" +
                "result='" + result + '\'' +
                ", baseCode='" + baseCode + '\'' +
                ", lastUpdate='" + timeLastUpdateUtc + '\'' +
                ", rates=" + conversionRates +
                '}';
    }
}
