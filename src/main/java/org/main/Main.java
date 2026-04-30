package org.main;


import org.adapters.exchangerate.ExchangeRateAdapter;
import org.adapters.exchangerate.results.*;
import org.core.helpers.HttpRequestHelper;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class



Main {
    static void main() {

        ExchangeRateAdapter adapter = ExchangeRateAdapter.fromFile("API_SETTINGS");

        // STANDARD  →  latest/USD
        HttpRequestHelper.sendRequest(
                        adapter.standard("USD").convert(),
                        StandardApiResult.class,
                        adapter
                ).onSuccess(r -> System.out.println("Base: " + r.getBaseCode()))
                .onSuccess(r -> System.out.println("SEK rate: " + r.getRate("SEK")))
                .onError(e -> System.out.println("Error: " + e.getErrorMessage()));

        // COMPARISON  →  pair/USD/SEK
        HttpRequestHelper.sendRequest(
                        adapter.comparison("USD", "SEK").convert(),
                        PairApiResult.class,
                        adapter
                ).onSuccess(r -> System.out.println("Rate: " + r.getConversionRate()))
                .onError(e -> System.out.println("Error: " + e.getErrorMessage()));

        // COMPARISON AMOUNT  →  pair/USD/SEK/150
        HttpRequestHelper.sendRequest(
                        adapter.comparisonAmount("USD", "SEK", 150).convert(),
                        PairAmountApiResult.class,
                        adapter
                ).onSuccess(r -> System.out.println("Result: " + r.getConversionResult()))
                .onError(e -> System.out.println("Error: " + e.getErrorMessage()));

        // CODES  →  codes
        HttpRequestHelper.sendRequest(
                        adapter.codes().convert(),
                        CurrencyCodeApiResult.class,
                        adapter
                ).onSuccess(r -> System.out.println("Total codes: " + r.getCodes().size()))
                .onError(e -> System.out.println("Error: " + e.getErrorMessage()));

        // QUOTA  →  quota
        HttpRequestHelper.sendRequest(
                        adapter.quota().convert(),
                        QuotaApiResult.class,
                        adapter
                ).onSuccess(r -> System.out.println("Remaining: " + r.getRequestsRemaining()))
                .onError(e -> System.out.println("Error: " + e.getErrorMessage()));
    }

}
