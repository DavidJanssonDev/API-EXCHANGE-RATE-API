package org.adapters.exchangerate.results;

import org.core.interfaces.IReturnType;

import java.util.List;

public class CurrencyCodeApiResult implements IReturnType {
    private final List<String[]> supportedCodes;

    public CurrencyCodeApiResult(List<String[]> supportedCodes) {
        this.supportedCodes = supportedCodes;
    }

    public List<String> getCodes() {
        return supportedCodes.stream().map(c -> c[0]).toList();
    }

    public String getName(String code) {
        return supportedCodes.stream()
                .filter(c -> c[0].equals(code))
                .map(c -> c[1])
                .findFirst()
                .orElse(null);
    }

    public List<String[]> getSupportedCodes() { return supportedCodes; }

    @Override
    public String toString() {
        return "CurrencyCodeApiResult{totalCodes=" + supportedCodes.size() + "}";
    }
}
