package org.httpApiClasses.ApiContext;

import org.httpApiClasses.CustomError.InvalidContentOfContext;
import org.httpApiClasses.Enums.SupportedEndPointEnum;
import org.httpApiClasses.Interface.IApiRequestValue;

import javax.management.ObjectName;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class ContextBodyObject {
    private final Map<String, Object> values;

    public ContextBodyObject(Map<String, Object> values) {
        this.values = values;
    }

    public static ContextBodyObject of(Object... pairs) throws InvalidContentOfContext {
        if (pairs.length % 2 != 0)
            throw new InvalidContentOfContext("Must provide key-value pairs");

        Map<String, Object> map = new LinkedHashMap<>();
        for (int i = 0; i < pairs.length; i += 2) {
            if (!(pairs[i] instanceof String))
                throw new InvalidContentOfContext("Key must be a String, get: " + pairs[i]);
            map.put((String) pairs[i], pairs[i + 1]);
        }
        return new ContextBodyObject(map);
    }
    public static <T extends IApiRequestValue> ContextBodyObject convertToApiRequestValue(T requestValue) {
        return requestValue.convert();
    }

    public String resolvePath(SupportedEndPointEnum endPoint) throws InvalidContentOfContext {
        String path = endPoint.getValue();

        for (Object val : values.values()) {
            if (!path.contains("CURRENCY") && !path.contains("AMOUNT"))
                break;
            path = path.replaceFirst("CURRENCY|AMOUNT", val.toString());
        }

        if (path.contains("CURRENCY") || path.contains("AMOUNT"))
            throw new InvalidContentOfContext(
                    "Not enough values to resolve endpoint: " + endPoint.getValue()
            );

        return path;
    }

    public Map<String, Object> getValues() { return values; }
}
