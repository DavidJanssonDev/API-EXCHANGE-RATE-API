package org.core.interfaces;


/**
 * Marker interface for all API response (return) types.
 *
 * <p>Any class that represents a successful response payload from an API must
 * implement this interface. It serves as the upper bound for the generic type
 * parameter {@code R} in {@link IApiAdapter} and
 * {@link org.core.holders.ResultHolder}, ensuring that only valid response
 * objects can flow through the framework's type system.</p>
 *
 * <p>The interface carries no methods; its sole purpose is type safety.</p>
 */
public interface IReturnType {}