package org.httpApiClasses.RetrunClasses;

import org.httpApiClasses.Interface.IReturnType;

import java.util.List;

public class CurrencyCodeApiResult implements IReturnType {
    private final String result;
    private final List<String[]> supportedCodes; // each entry is [code, name] e.g. ["USD", "US Dollar"]

    public CurrencyCodeApiResult(String result, List<String[]> supportedCodes) {
        this.result         = result;
        this.supportedCodes = supportedCodes;
    }

    // Get just the currency codes e.g. "USD", "EUR"
    public List<String> getCodes() {
        return supportedCodes.stream().map(c -> c[0]).toList();
    }

    // Get the name for a code e.g. "USD" → "US Dollar"
    public String getName(String code) {
        return supportedCodes.stream()
                .filter(c -> c[0].equals(code))
                .map(c -> c[1])
                .findFirst()
                .orElse(null);
    }

    public String getResult()                  { return result; }
    public List<String[]> getSupportedCodes()  { return supportedCodes; }

    @Override
    public String toString() {
        return "CurrencyCodeApiResult{" +
                "result='" + result + '\'' +
                ", totalCodes=" + supportedCodes.size() +
                '}';
    }
}
