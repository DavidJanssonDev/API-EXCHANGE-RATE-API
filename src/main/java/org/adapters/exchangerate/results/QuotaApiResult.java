package org.adapters.exchangerate.results;

import org.core.interfaces.IReturnType;

public class QuotaApiResult implements IReturnType {
    private final int planQuota;
    private final int requestsRemaining;
    private final int refreshDayOfMonth;

    public QuotaApiResult(int planQuota, int requestsRemaining, int refreshDayOfMonth) {
        this.planQuota          = planQuota;
        this.requestsRemaining  = requestsRemaining;
        this.refreshDayOfMonth  = refreshDayOfMonth;
    }

    public int getPlanQuota()          { return planQuota; }
    public int getRequestsRemaining()  { return requestsRemaining; }
    public int getRefreshDayOfMonth()  { return refreshDayOfMonth; }

    @Override
    public String toString() {
        return "QuotaApiResult{planQuota=" + planQuota +
                ", remaining=" + requestsRemaining +
                ", refreshDay=" + refreshDayOfMonth + "}";
    }
}
