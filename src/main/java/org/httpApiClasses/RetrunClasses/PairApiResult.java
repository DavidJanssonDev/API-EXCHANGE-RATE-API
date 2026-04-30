package org.httpApiClasses.RetrunClasses;

import org.httpApiClasses.Interface.IReturnType;

public class PairApiResult implements IReturnType {
    private final String result;
    private final long timeLastUpdateUnix;
    private final String timeLastUpdateUtc;
    private final long timeNextUpdateUnix;
    private final String timeNextUpdateUtc;
    private final String baseCode;
    private final String targetCode;
    private final double conversionRate;

    public PairApiResult(String result, long timeLastUpdateUnix, String timeLastUpdateUtc,
                         long timeNextUpdateUnix, String timeNextUpdateUtc,
                         String baseCode, String targetCode, double conversionRate) {
        this.result             = result;
        this.timeLastUpdateUnix = timeLastUpdateUnix;
        this.timeLastUpdateUtc  = timeLastUpdateUtc;
        this.timeNextUpdateUnix = timeNextUpdateUnix;
        this.timeNextUpdateUtc  = timeNextUpdateUtc;
        this.baseCode           = baseCode;
        this.targetCode         = targetCode;
        this.conversionRate     = conversionRate;
    }

    public String getResult()            { return result; }
    public long getTimeLastUpdateUnix()  { return timeLastUpdateUnix; }
    public String getTimeLastUpdateUtc() { return timeLastUpdateUtc; }
    public long getTimeNextUpdateUnix()  { return timeNextUpdateUnix; }
    public String getTimeNextUpdateUtc() { return timeNextUpdateUtc; }
    public String getBaseCode()          { return baseCode; }
    public String getTargetCode()        { return targetCode; }
    public double getConversionRate()    { return conversionRate; }

    @Override
    public String toString() {
        return "PairApiResult{" +
                "result='" + result + '\'' +
                ", baseCode='" + baseCode + '\'' +
                ", targetCode='" + targetCode + '\'' +
                ", conversionRate=" + conversionRate +
                '}';
    }
}
