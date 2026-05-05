package org.main;


import org.exempleCode.exchangerate.ExchangeRateAdapter;
import org.exempleCode.exchangerate.results.*;
import org.core.helpers.HttpRequestBuilder;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class



Main {
    static void main() {

        ExchangeRateAdapter apiRequestTypeBuilder = ExchangeRateAdapter.fromFile("API_SETTINGS");

        // STANDARD  →  latest/USD
        HttpRequestBuilder.sendRequest(
                        apiRequestTypeBuilder.standard("USD").convert(),
                        StandardApiResult.class,
                        apiRequestTypeBuilder
                ).onSuccess(r -> System.out.println("Base: " + r.getBaseCode()))
                .onSuccess(r -> System.out.println("SEK rate: " + r.getRate("SEK")))
                .onError(e -> System.out.println("Error: " + e.getErrorMessage()));

        // COMPARISON  →  pair/USD/SEK
        HttpRequestBuilder.sendRequest(
                        apiRequestTypeBuilder.comparison("USD", "SEK").convert(),
                        PairApiResult.class,
                        apiRequestTypeBuilder
                ).onSuccess(r -> System.out.println("Rate: " + r.getConversionRate()))
                .onError(e -> System.out.println("Error: " + e.getErrorMessage()));

        // COMPARISON AMOUNT  →  pair/USD/SEK/150
        HttpRequestBuilder.sendRequest(
                        apiRequestTypeBuilder.comparisonAmount("USD", "SEK", 150).convert(),
                        PairAmountApiResult.class,
                        apiRequestTypeBuilder
                ).onSuccess(r -> System.out.println("Result: " + r.getConversionResult()))
                .onError(e -> System.out.println("Error: " + e.getErrorMessage()));

        // CODES  →  codes
        HttpRequestBuilder.sendRequest(
                        apiRequestTypeBuilder.codes().convert(),
                        CurrencyCodeApiResult.class,
                        apiRequestTypeBuilder
                ).onSuccess(r -> System.out.println("Total codes: " + r.getCodes().size()))
                .onError(e -> System.out.println("Error: " + e.getErrorMessage()));

        // QUOTA  →  quota
        HttpRequestBuilder.sendRequest(
                        apiRequestTypeBuilder.quota().convert(),
                        QuotaApiResult.class,
                        apiRequestTypeBuilder
                ).onSuccess(r -> System.out.println("Remaining: " + r.getRequestsRemaining()))
                .onError(e -> System.out.println("Error: " + e.getErrorMessage()));
    }

}
