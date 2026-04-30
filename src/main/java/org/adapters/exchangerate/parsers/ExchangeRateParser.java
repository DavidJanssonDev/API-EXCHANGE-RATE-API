package org.adapters.exchangerate.parsers;

import org.adapters.exchangerate.results.*;
import org.core.interfaces.IReturnType;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ExchangeRateParser {

    private static final Map<Class<? extends IReturnType>,
            Function<String, ? extends IReturnType>> parsers = new HashMap<>();

    static {
        parsers.put(StandardApiResult.class,     ExchangeRateParser::parseStandard);
        parsers.put(PairApiResult.class,         ExchangeRateParser::parsePair);
        parsers.put(PairAmountApiResult.class,   ExchangeRateParser::parsePairAmount);
        parsers.put(CurrencyCodeApiResult.class, ExchangeRateParser::parseCurrencyCodes);
        parsers.put(QuotaApiResult.class,        ExchangeRateParser::parseQuota);
    }

    @SuppressWarnings("unchecked")
    public static <R extends IReturnType> R parse(String json, Class<R> type) {
        Function<String, ? extends IReturnType> parser = parsers.get(type);
        if (parser == null)
            throw new UnsupportedOperationException("No parser for: " + type.getSimpleName());
        return (R) parser.apply(json);
    }

    private static StandardApiResult parseStandard(String json) {
        JSONObject obj   = new JSONObject(json);
        JSONObject rates = obj.getJSONObject("conversion_rates");
        Map<String, Double> conversionRates = new HashMap<>();
        for (String key : rates.keySet())
            conversionRates.put(key, rates.getDouble(key));
        return new StandardApiResult(
                obj.getString("result"),
                obj.getString("base_code"),
                obj.getString("time_last_update_utc"),
                obj.getString("time_next_update_utc"),
                conversionRates
        );
    }

    private static PairApiResult parsePair(String json) {
        JSONObject obj = new JSONObject(json);
        return new PairApiResult(
                obj.getString("base_code"),
                obj.getString("target_code"),
                obj.getDouble("conversion_rate")
        );
    }

    private static PairAmountApiResult parsePairAmount(String json) {
        JSONObject obj = new JSONObject(json);
        return new PairAmountApiResult(
                obj.getString("base_code"),
                obj.getString("target_code"),
                obj.getDouble("conversion_rate"),
                obj.getDouble("conversion_result")
        );
    }

    private static CurrencyCodeApiResult parseCurrencyCodes(String json) {
        JSONObject obj       = new JSONObject(json);
        JSONArray codesArray = obj.getJSONArray("supported_codes");
        List<String[]> codes = new ArrayList<>();
        for (int i = 0; i < codesArray.length(); i++) {
            JSONArray pair = codesArray.getJSONArray(i);
            codes.add(new String[]{ pair.getString(0), pair.getString(1) });
        }
        return new CurrencyCodeApiResult(codes);
    }

    private static QuotaApiResult parseQuota(String json) {
        JSONObject obj = new JSONObject(json);
        return new QuotaApiResult(
                obj.getInt("plan_quota"),
                obj.getInt("requests_remaining"),
                obj.getInt("refresh_day_of_month")
        );
    }

}
