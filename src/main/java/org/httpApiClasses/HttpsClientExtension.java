package org.httpApiClasses;

import org.helpers.FileReaderHelper;
import org.httpApiClasses.ApiContext.ContextBodyObject;
import org.httpApiClasses.CustomError.InvalidContentOfContext;
import org.httpApiClasses.Enums.ApiKeyStyle;
import org.httpApiClasses.Enums.SupportedEndPointEnum;
import org.httpApiClasses.Helpers.ReturnTypeHelper;
import org.httpApiClasses.Interface.IReturnType;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

public class HttpsClientExtension {
    private final URI baseURI;
    private final HttpClient baseHttpClient;
    private final HttpRequest.Builder baseBuilder;
    private final String baseApiKey;
    private final String baseApiUrl;
    private final ApiKeyStyle keyStyle;

//<----- CONSTRUCTOR ----->

    private HttpsClientExtension(String baseApiKey, String baseApiUrl, ApiKeyStyle keyStyle) {
        this.baseBuilder    = HttpRequest.newBuilder();
        this.baseHttpClient = HttpClient.newHttpClient();
        this.baseURI        = URI.create(baseApiUrl);
        this.baseApiUrl     = baseApiUrl;
        this.baseApiKey     = baseApiKey;
        this.keyStyle       = keyStyle;
    }

    public static HttpsClientExtension fromFile(String fileName) {
        RetrunFileType apiValues = readApiValuesFromFile(fileName);
        return new HttpsClientExtension(apiValues.apiKey(), apiValues.apiURl(), ApiKeyStyle.PATH);
    }

    public static HttpsClientExtension fromApiKey(String apiKey, String baseUrl, ApiKeyStyle keyStyle) {
        return new HttpsClientExtension(apiKey, baseUrl, keyStyle);
    }

    private static RetrunFileType readApiValuesFromFile(String path) {
        HashMap<String, String> values = FileReaderHelper.readFromFile(path);
        return new RetrunFileType(values.get("api_key"), values.get("base_api_link"));
    }

//<-----       METHOD      ----->

    public <T extends IReturnType> T send(SupportedEndPointEnum endPoint, ContextBodyObject context) {
        try {
            String resolvedPath = context.resolvePath(endPoint);
            URI resolvedURI     = buildURI(resolvedPath);

            HttpRequest request = baseBuilder
                    .uri(resolvedURI)
                    .GET()
                    .build();

            HttpResponse<String> response = baseHttpClient
                    .send(request, HttpResponse.BodyHandlers.ofString());

            return (T) ReturnTypeHelper.parse(response.body(), endPoint.getReturnType());

        } catch (InvalidContentOfContext e) {
            throw new IllegalArgumentException("Invalid context: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Request failed: " + e.getMessage(), e);
        }
    }

    private URI buildURI(String resolvedPath) {
        return switch (keyStyle) {
            case PATH  -> baseURI.resolve(baseApiKey + "/" + resolvedPath);
            case QUERY -> URI.create(baseApiUrl + "/" + resolvedPath + "?apikey=" + baseApiKey);
        };
    }

//<----- GETTERS / SETTERS ----->

    public String getBaseApiKey() { return baseApiKey; }
    public String getBaseApiUrl() { return baseApiUrl; }
    public ApiKeyStyle getKeyStyle() { return keyStyle; }
}

record RetrunFileType(String apiKey, String apiURl) {}