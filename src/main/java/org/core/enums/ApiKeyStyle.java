package org.core.enums;

public enum ApiKeyStyle {
    PATH,   // https://api.com/API_KEY/endpoint
    QUERY,  // https://api.com/endpoint?apikey=API_KEY
    NONE    // https://api.com/endpoint
}
