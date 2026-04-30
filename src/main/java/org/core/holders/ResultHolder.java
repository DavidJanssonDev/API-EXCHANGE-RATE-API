package org.core.holders;

import org.core.interfaces.IRequestError;
import org.core.interfaces.IReturnType;

import java.util.function.Consumer;

public class ResultHolder<R extends IReturnType, IE extends IRequestError> {
    private final R result;
    private final IE error;
    private final boolean success;

    private ResultHolder(R result, IE error, boolean success) {
        this.result  = result;
        this.error   = error;
        this.success = success;
    }

    public static <R extends IReturnType, IE extends IRequestError> ResultHolder<R, IE> success(R result) {
        return new ResultHolder<>(result, null, true);
    }

    public static <R extends IReturnType, IE extends IRequestError> ResultHolder<R, IE> error(IE error) {
        return new ResultHolder<>(null, error, false);
    }

    public ResultHolder<R, IE> onSuccess(Consumer<R> handler) {
        if (success) handler.accept(result);
        return this;
    }

    public ResultHolder<R, IE> onError(Consumer<IE> handler) {
        if (!success) handler.accept(error);
        return this;
    }

    public boolean isSuccess() { return success; }
    public R getResult() { return result; }
    public IE getError() { return error; }
}
