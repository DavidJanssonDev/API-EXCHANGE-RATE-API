package org.core.interfaces;


/**
 * Marker interface for typed API error objects.
 *
 * <p>Each distinct error condition that a specific API can return should be
 * represented as a class (or enum constant) implementing this interface.
 * Instances are stored in the map returned by {@link IApiAdapter#getErrorMap()}
 * and surfaced to the caller through {@link org.core.holders.ResultHolder#onError}.</p>
 *
 * <p>Example implementation:</p>
 * <pre>{@code
 * public enum ExchangeRateError implements IRequestError {
 *     UNSUPPORTED_CODE("unsupported-code", "The currency code is not supported."),
 *     QUOTA_REACHED   ("quota-reached",    "Monthly request quota has been exceeded.");
 *
 *     private final String code;
 *     private final String message;
 *
 *     ExchangeRateError(String code, String message) {
 *         this.code    = code;
 *         this.message = message;
 *     }
 *
 *     public String getErrorCode()    { return code; }
 *     public String getErrorMessage() { return message; }
 * }
 * }</pre>
 */
public interface IRequestError {
    /**
     * Returns the machine-readable error code exactly as the API sends it.
     *
     * <p>This value must match the key used in {@link IApiAdapter#getErrorMap()}.</p>
     *
     * @return non-null error code string
     */
    String getErrorCode();

    /**
     * Returns a human-readable description of the error.
     *
     * @return non-null message string suitable for logging or display
     */
    String getErrorMessage();
}
