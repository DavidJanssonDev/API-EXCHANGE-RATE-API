package org.httpApiClasses.Helpers;

import org.httpApiClasses.Interface.IReturnType;
import org.httpApiClasses.RetrunClasses.PairAmountApiResult;
import org.httpApiClasses.RetrunClasses.PairApiResult;
import org.httpApiClasses.RetrunClasses.StandardApiResult;
import org.httpApiClasses.RetrunClasses.*;

import java.util.HashMap;
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
        // fill in once JSON shape is known
        return new StandardApiResult();
    }

    private static PairApiResult parsePair(String json) {
        // fill in once JSON shape is known
        return new PairApiResult();
    }

    private static PairAmountApiResult parsePairAmount(String json) {
        // fill in once JSON shape is known
        return new PairAmountApiResult();
    }

    private static CurrencyCodeApiResult parseCurrencyCodes(String json) {
        return new CurrencyCodeApiResult();
    }

    private static QuotaApiResult parseQuota(String json) {
        return new QuotaApiResult();
    }
}