package org.core.holders;

import org.core.interfaces.IRequestError;
import org.core.interfaces.IReturnType;

import java.util.function.Consumer;

/**
 * A discriminated union that holds either a successful API result ({@code R})
 * or a typed error ({@code IE}) — but never both.
 *
 * <p>This avoids exception-based error handling in the calling code. Instead,
 * callers register callbacks through {@link #onSuccess} and {@link #onError},
 * and only the appropriate callback is invoked:</p>
 *
 * <pre>{@code
 * HttpRequestBuilder.sendRequest(holder, StandardApiResult.class, adapter)
 *     .onSuccess(r -> System.out.println("Base: " + r.getBaseCode()))
 *     .onError(e   -> System.out.println("Error: " + e.getErrorMessage()));
 * }</pre>
 *
 * <p>Both callback methods return {@code this}, so multiple handlers can be
 * chained. Instances are created only through the static factory methods
 * {@link #success(IReturnType)} and {@link #error(IRequestError)}.</p>
 *
 * @param <R>  the success result type; must extend {@link IReturnType}
 * @param <IE> the error type; must extend {@link IRequestError}
 */
public class ResultHolder<R extends IReturnType, IE extends IRequestError> {
    private final R result;
    private final IE error;
    private final boolean success;

    private ResultHolder(R result, IE error, boolean success) {
        this.result  = result;
        this.error   = error;
        this.success = success;
    }

    // ----------------------------------------------------------------
    //  Static factories
    // ----------------------------------------------------------------

    /**
     * Creates a successful {@code ResultHolder} wrapping the given result.
     *
     * @param result the parsed API response; must not be {@code null}
     * @param <R>    result type
     * @param <IE>   error type (inferred; not used in a success holder)
     * @return a holder where {@link #isSuccess()} returns {@code true}
     */
    public static <R extends IReturnType, IE extends IRequestError> ResultHolder<R, IE> success(R result) {
        return new ResultHolder<>(result, null, true);
    }

    /**
     * Creates a failed {@code ResultHolder} wrapping the given error.
     *
     * @param error the typed error object; must not be {@code null}
     * @param <R>   result type (inferred; not used in an error holder)
     * @param <IE>  error type
     * @return a holder where {@link #isSuccess()} returns {@code false}
     */
    public static <R extends IReturnType, IE extends IRequestError> ResultHolder<R, IE> error(IE error) {
        return new ResultHolder<>(null, error, false);
    }

    // ----------------------------------------------------------------
    //  Fluent callbacks
    // ----------------------------------------------------------------

    /**
     * Invokes {@code handler} with the result if this holder represents success.
     *
     * <p>The handler is a no-op when this holder holds an error.</p>
     *
     * @param handler consumer to receive the result; must not be {@code null}
     * @return {@code this}, for chaining further callbacks
     */
    public ResultHolder<R, IE> onSuccess(Consumer<R> handler) {
        if (success) handler.accept(result);
        return this;
    }

    /**
     * Invokes {@code handler} with the error if this holder represents failure.
     *
     * <p>The handler is a no-op when this holder holds a successful result.</p>
     *
     * @param handler consumer to receive the error; must not be {@code null}
     * @return {@code this}, for chaining further callbacks
     */
    public ResultHolder<R, IE> onError(Consumer<IE> handler) {
        if (!success) handler.accept(error);
        return this;
    }

    // ----------------------------------------------------------------
    //  Direct accessors (use sparingly; prefer the callback API above)
    // ----------------------------------------------------------------

    /** @return {@code true} if this holder wraps a successful result */
    public boolean isSuccess() { return success; }

    /**
     * Returns the result directly.
     *
     * @return the result, or {@code null} if this is an error holder
     */
    public R getResult() { return result; }

    /**
     * Returns the error directly.
     *
     * @return the error, or {@code null} if this is a success holder
     */
    public IE getError() { return error; }
}
