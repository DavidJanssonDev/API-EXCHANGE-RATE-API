# API-EXCHANGE-RATE-API

A generic, extensible HTTP adapter framework for Java — demonstrated with the [ExchangeRate-API](https://www.exchangerate-api.com/). The core is API-agnostic and can be reused for any REST service.

---

## Table of Contents

- [Project Structure](#project-structure)
- [Core Architecture](#core-architecture)
    - [Interfaces](#interfaces)
    - [Holders](#holders)
    - [Helpers](#helpers)
    - [Enums](#enums)
- [How It Works](#how-it-works)
- [Usage Example](#usage-example)
- [Extending to a New API](#extending-to-a-new-api)
- [Configuration](#configuration)

---

## Project Structure

```
src/main/java/org/
├── core/
│   ├── enums/
│   │   ├── ApiKeyStyle.java       # How the API key is injected into the request
│   │   └── HttpMethod.java        # Supported HTTP verbs
│   ├── helpers/
│   │   ├── FileReaderHelper.java  # Reads key=value config files from classpath
│   │   └── HttpRequestBuilder.java # Sends HTTP requests and maps responses
│   ├── holders/
│   │   ├── RequestHolder.java     # Immutable container for a pending request
│   │   └── ResultHolder.java      # Typed result: success or error
│   └── interfaces/
│       ├── IApiAdapter.java       # Contract for any API adapter implementation
│       ├── IApiRequestValue.java  # Contract for request parameter objects
│       ├── IRequestError.java     # Contract for typed API errors
│       └── IReturnType.java       # Marker interface for all API response types
└── adapters/
    └── exchangerate/              # ExchangeRate-API specific implementation
        └── ...
```

---

## Core Architecture

The core is designed around four layers: **interfaces**, **holders**, **helpers**, and **enums**. Together they form a reusable pipeline for any HTTP REST API.

---

### Interfaces

#### `IApiAdapter<R, IE>`

The central contract that every API adapter must implement. It defines how requests should be authenticated, how responses are parsed, and how errors are mapped.

```java
public interface IApiAdapter<R extends IReturnType, IE extends IRequestError>
```

| Method | Description |
|---|---|
| `getBaseUri()` | Returns the root URI of the API. |
| `getApiKey()` | Returns the API key wrapped in `Optional` (empty = no key). |
| `getKeyStyle()` | Specifies how the API key is attached (`PATH`, `QUERY`, or `NONE`). |
| `getSuccessCondition()` | A `Predicate<JSONObject>` that determines if a response represents success. |
| `getErrorField()` | The JSON field name that contains the error code on failure. |
| `getErrorMap()` | A map from error code strings to typed `IRequestError` instances. |
| `parseSuccess(String json, Class<R>)` | Deserialises a successful JSON body into the correct return type. |

---

#### `IApiRequestValue`

Implemented by all request parameter objects (e.g. "get rates for USD"). Forces each request type to produce a `RequestHolder` that the HTTP layer can consume.

```java
public interface IApiRequestValue {
    RequestHolder convert();
}
```

---

#### `IReturnType`

Marker interface. All API response model classes must implement this so they can be used as the generic type parameter `R` in `IApiAdapter` and `ResultHolder`.

```java
public interface IReturnType {}
```

---

#### `IRequestError`

Contract for typed API error objects. Each distinct error a specific API can return (e.g. `"unsupported-code"`, `"quota-reached"`) is represented as a class implementing this interface.

```java
public interface IRequestError {
    String getErrorCode();
    String getErrorMessage();
}
```

---

### Holders

#### `RequestHolder`

An immutable value object that holds everything needed to fire a single HTTP request: URI, method, headers, and an optional body. Built via a fluent builder.

```java
RequestHolder request = new RequestHolder.Builder(uri, HttpMethod.GET)
    .header("Accept", "application/json")
    .build();
```

| Field | Type | Description |
|---|---|---|
| `uri` | `URI` | The fully-resolved endpoint URL. |
| `method` | `HttpMethod` | GET, POST, PUT, DELETE, PATCH. |
| `headers` | `Map<String, String>` | HTTP request headers. |
| `body` | `Optional<String>` | Request body, absent for GET/DELETE. |

---

#### `ResultHolder<R, IE>`

A discriminated union that holds either a successful result (`R`) or a typed error (`IE`). Supports a fluent, callback-based API for handling both outcomes without exception handling in calling code.

```java
ResultHolder.success(result)   // wraps a successful response
ResultHolder.error(error)      // wraps a typed error

// Fluent consumption:
holder
    .onSuccess(r -> System.out.println(r.getBaseCode()))
    .onError(e  -> System.out.println(e.getErrorMessage()));
```

Both `onSuccess` and `onError` return `this`, allowing chaining. Only the matching callback is invoked.

---

### Helpers

#### `HttpRequestBuilder`

Static utility class that handles the full request lifecycle. Accepts a `RequestHolder` and an `IApiAdapter`, fires the HTTP request using Java's built-in `HttpClient`, inspects the JSON response, and returns a `ResultHolder`.

**Flow:**

```
RequestHolder + IApiAdapter
        │
        ▼
  Build HttpRequest
        │
        ▼
  Send via HttpClient
        │
        ▼
  Parse JSON body
        │
   ┌────┴────┐
success?   error?
   │           │
parseSuccess  look up error
   │         in getErrorMap()
   ▼              │
ResultHolder.success  ResultHolder.error
```

Key method:

```java
public static <R extends IReturnType, IE extends IRequestError>
ResultHolder<R, IE> sendRequest(
    RequestHolder requestHolder,
    Class<R> returnType,
    IApiAdapter<?, ?> adapter
)
```

Supports all five HTTP verbs: `GET`, `POST`, `PUT`, `DELETE`, and `PATCH`.

---

#### `FileReaderHelper`

Reads a simple `key=value` configuration file from the classpath (e.g. `resources/API_SETTINGS`). Skips blank lines and lines starting with `#`. Strips surrounding double-quotes from values.

```
# API_SETTINGS
API_KEY="your-api-key-here"
BASE_URL="https://v6.exchangerate-api.com/v6"
```

```java
HashMap<String, String> settings = FileReaderHelper.readFromFile("API_SETTINGS");
```

---

### Enums

#### `ApiKeyStyle`

Controls how the API key is injected into the outgoing request URL.

| Value | URL pattern |
|---|---|
| `PATH` | `https://api.com/{API_KEY}/endpoint` |
| `QUERY` | `https://api.com/endpoint?apikey={API_KEY}` |
| `NONE` | `https://api.com/endpoint` (no key) |

---

## How It Works

The full pipeline for a single API call:

```
1. Adapter.someRequestType(params)
        │  returns IApiRequestValue
        ▼
2. .convert()
        │  returns RequestHolder (fully-resolved URI + headers)
        ▼
3. HttpRequestBuilder.sendRequest(holder, ReturnType.class, adapter)
        │  fires HttpClient, reads body
        ▼
4. adapter.getSuccessCondition().test(json)
        ├─ true  → adapter.parseSuccess(json, class) → ResultHolder.success(R)
        └─ false → adapter.getErrorMap().get(errorKey) → ResultHolder.error(IE)
        ▼
5. .onSuccess(...).onError(...)
```

---

## Usage Example

```java
ExchangeRateAdapter adapter = ExchangeRateAdapter.fromFile("API_SETTINGS");

// Get all exchange rates for USD
HttpRequestBuilder.sendRequest(
    adapter.standard("USD").convert(),
    StandardApiResult.class,
    adapter
)
.onSuccess(r -> System.out.println("SEK: " + r.getRate("SEK")))
.onError(e   -> System.out.println("Error: " + e.getErrorMessage()));

// Convert a specific amount
HttpRequestBuilder.sendRequest(
    adapter.comparisonAmount("USD", "SEK", 150).convert(),
    PairAmountApiResult.class,
    adapter
)
.onSuccess(r -> System.out.println("Result: " + r.getConversionResult()))
.onError(e   -> System.out.println("Error: " + e.getErrorMessage()));
```

---

## Extending to a New API

1. Create a class that implements `IApiAdapter<YourResult, YourError>`.
2. Create request parameter classes implementing `IApiRequestValue` — each `convert()` builds the correct `RequestHolder`.
3. Create result classes implementing `IReturnType` and error classes implementing `IRequestError`.
4. Call `HttpRequestBuilder.sendRequest(...)` with your adapter.

The core layer requires no modification.

---

## Configuration

Place an `API_SETTINGS` file in `src/main/resources/`:

```
API_KEY="your-key-here"
BASE_URL="https://v6.exchangerate-api.com/v6"
```

Lines beginning with `#` are treated as comments and ignored.
