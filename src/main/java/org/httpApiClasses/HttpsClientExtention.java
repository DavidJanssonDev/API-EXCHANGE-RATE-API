package org.httpApiClasses;

import java.net.URI;
import java.net.http.HttpClient;

public class HttpsClientExtention {
    private URI baseURI;
    private HttpClient baseHttpClient;
    private final String baseApiKey;
    private final String baseApiUrl;

    private HttpsClientExtention(String baseAPiKey, String baseApiUrl) {
        this.baseApiUrl = baseApiUrl;
        this.baseApiKey = baseAPiKey;
    }

    public static HttpsClientExtention fromFIle(String filePath) {
        RetrunFileType apiValues = readApiValuesFromFile(filePath);

        return  new HttpsClientExtention(apiValues.apiKey(), apiValues.apiURl());
    }

    public static HttpsClientExtention fromAPIKey(String apiKey, String baseUrl){
        return new HttpsClientExtention(apiKey, baseUrl);
    }

    private static RetrunFileType readApiValuesFromFile(String path){
        // TODO: Implement getting values from file
        return  new RetrunFileType("API_KEY","https://exemple.com/api");
    }





}


record RetrunFileType(String apiKey, String apiURl){

}
