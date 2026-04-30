package org.httpApiClasses.Helpers;

import org.httpApiClasses.Interface.IReturnType;
import org.httpApiClasses.RetrunClasses.PairAmountApiResult;
import org.httpApiClasses.RetrunClasses.PairApiResult;
import org.httpApiClasses.RetrunClasses.StandardApiResult;
import org.httpApiClasses.RetrunClasses.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ReturnTypeHelper {

    private static final Map<Class<? extends IReturnType>, Function<String, ? extends IReturnType>> parsers = new HashMap<>();

    static {
        parsers.put(StandardApiResult.class,     ReturnTypeHelper::parseStandard);
        parsers.put(PairApiResult.class,         ReturnTypeHelper::parsePair);
        parsers.put(PairAmountApiResult.class,   ReturnTypeHelper::parsePairAmount);
        parsers.put(CurrencyCodeApiResult.class, ReturnTypeHelper::parseCurrencyCodes);  // ← add
        parsers.put(QuotaApiResult.class,        ReturnTypeHelper::parseQuota);          // ← add
    }




    public static <T extends IReturnType> T parse(String json, Class<T> type) {
        Function<String, ? extends IReturnType> parser = parsers.get(type);

        if (parser == null)
            throw new UnsupportedOperationException("No parser registered for: " + type.getSimpleName());

        return (T) parser.apply(json);
    }

    private static StandardApiResult parseStandard(String json) {
        JSONObject obj   = new JSONObject(json);
        JSONObject rates = obj.getJSONObject("conversion_rates");

        Map<String, Double> conversionRates = new HashMap<>();
        for (String key : rates.keySet())
            conversionRates.put(key, rates.getDouble(key));

        return new StandardApiResult(
                obj.getString("result"),
                obj.getLong("time_last_update_unix"),
                obj.getString("time_last_update_utc"),
                obj.getLong("time_next_update_unix"),
                obj.getString("time_next_update_utc"),
                obj.getString("base_code"),
                conversionRates
        );
    }

    private static PairApiResult parsePair(String json) {
        JSONObject obj = new JSONObject(json);
        return new PairApiResult(
                obj.getString("result"),
                obj.getLong("time_last_update_unix"),
                obj.getString("time_last_update_utc"),
                obj.getLong("time_next_update_unix"),
                obj.getString("time_next_update_utc"),
                obj.getString("base_code"),
                obj.getString("target_code"),
                obj.getDouble("conversion_rate")
        );
    }

    private static PairAmountApiResult parsePairAmount(String json) {
        JSONObject obj = new JSONObject(json);
        return new PairAmountApiResult(
                obj.getString("result"),
                obj.getLong("time_last_update_unix"),
                obj.getString("time_last_update_utc"),
                obj.getLong("time_next_update_unix"),
                obj.getString("time_next_update_utc"),
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

        return new CurrencyCodeApiResult(obj.getString("result"), codes);
    }

    private static QuotaApiResult parseQuota(String json) {
        JSONObject obj = new JSONObject(json);
        return new QuotaApiResult(
                obj.getString("result"),
                obj.getInt("plan_quota"),
                obj.getInt("requests_remaining"),
                obj.getInt("refresh_day_of_month")
        );
    }
}