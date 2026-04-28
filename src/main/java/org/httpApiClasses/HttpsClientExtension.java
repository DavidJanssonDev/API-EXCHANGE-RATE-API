package org.httpApiClasses;

import java.io.InputStream;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

public class HttpsClientExtension {
    private URI baseURI;
    private HttpClient baseHttpClient;
    private final String baseApiKey;
    private final String baseApiUrl;
    private final HttpRequest.Builder baseBuilder;

    private HttpsClientExtension(String baseAPiKey, String baseApiUrl) {
        this.baseBuilder = HttpRequest.newBuilder();
        this.baseHttpClient = HttpClient.newHttpClient();
        this.baseURI = URI.create(baseApiUrl);
        this.baseApiUrl = baseApiUrl;
        this.baseApiKey = baseAPiKey;
    }

    public static HttpsClientExtension fromFile(String filePath) {
        RetrunFileType apiValues = readApiValuesFromFile(filePath);
        return new HttpsClientExtension(apiValues.apiKey(), apiValues.apiURl());
    }

    public static HttpsClientExtension fromAPIKey(String apiKey, String baseUrl){
        return new HttpsClientExtension(apiKey, baseUrl);
    }

    private static RetrunFileType readApiValuesFromFile(String path){


        return new RetrunFileType("API_KEY","https://exemple.com/api");
    }





}


record RetrunFileType(String apiKey, String apiURl){}
