package org.adapters.exchangerate.errors;

import org.core.interfaces.IRequestError;

public class InvalidKeyError implements IRequestError {
    @Override public String getErrorCode()    { return "invalid-key"; }
    @Override public String getErrorMessage() { return "The provided API key is invalid"; }
}
