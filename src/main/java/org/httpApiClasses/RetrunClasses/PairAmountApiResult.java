package org.httpApiClasses.RetrunClasses;

import org.httpApiClasses.Interface.IReturnType;

public class PairAmountApiResult implements IReturnType {
    private final String result;
    private final long timeLastUpdateUnix;
    private final String timeLastUpdateUtc;
    private final long timeNextUpdateUnix;
    private final String timeNextUpdateUtc;
    private final String baseCode;
    private final String targetCode;
    private final double conversionRate;
    private final double conversionResult;

    public PairAmountApiResult(
        String result,
        long timeLastUpdateUnix,
        String timeLastUpdateUtc,
        long timeNextUpdateUnix,
        String timeNextUpdateUtc,
        String baseCode,
        String targetCode,
        double conversionRate,
        double conversionResult
    ) {
        this.result             = result;
        this.timeLastUpdateUnix = timeLastUpdateUnix;
        this.timeLastUpdateUtc  = timeLastUpdateUtc;
        this.timeNextUpdateUnix = timeNextUpdateUnix;
        this.timeNextUpdateUtc  = timeNextUpdateUtc;
        this.baseCode           = baseCode;
        this.targetCode         = targetCode;
        this.conversionRate     = conversionRate;
        this.conversionResult   = conversionResult;
    }

    public String getResult()            { return result; }
    public long getTimeLastUpdateUnix()  { return timeLastUpdateUnix; }
    public String getTimeLastUpdateUtc() { return timeLastUpdateUtc; }
    public long getTimeNextUpdateUnix()  { return timeNextUpdateUnix; }
    public String getTimeNextUpdateUtc() { return timeNextUpdateUtc; }
    public String getBaseCode()          { return baseCode; }
    public String getTargetCode()        { return targetCode; }
    public double getConversionRate()    { return conversionRate; }
    public double getConversionResult()  { return conversionResult; }

    @Override
    public String toString() {
        return "PairAmountApiResult{" +
                "result='" + result + '\'' +
                ", baseCode='" + baseCode + '\'' +
                ", targetCode='" + targetCode + '\'' +
                ", conversionRate=" + conversionRate +
                ", conversionResult=" + conversionResult +
                '}';
    }
}
