package org.httpApiClasses.RetrunClasses;

import org.httpApiClasses.Interface.IReturnType;

public class QuotaApiResult implements IReturnType {
    private final String result;
    private final int planQuota;
    private final int requestsRemaining;
    private final int refreshDayOfMonth;

    public QuotaApiResult(String result, int planQuota, int requestsRemaining, int refreshDayOfMonth) {
        this.result              = result;
        this.planQuota           = planQuota;
        this.requestsRemaining   = requestsRemaining;
        this.refreshDayOfMonth   = refreshDayOfMonth;
    }

    public String getResult()          { return result; }
    public int getPlanQuota()          { return planQuota; }
    public int getRequestsRemaining()  { return requestsRemaining; }
    public int getRefreshDayOfMonth()  { return refreshDayOfMonth; }

    @Override
    public String toString() {
        return "QuotaApiResult{" +
                "result='" + result + '\'' +
                ", planQuota=" + planQuota +
                ", requestsRemaining=" + requestsRemaining +
                ", refreshDayOfMonth=" + refreshDayOfMonth +
                '}';
    }
}
