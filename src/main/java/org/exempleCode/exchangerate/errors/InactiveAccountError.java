package org.exempleCode.exchangerate.errors;

import org.core.interfaces.IRequestError;

public class InactiveAccountError implements IRequestError {
    @Override public String getErrorCode()    { return "inactive-account"; }
    @Override public String getErrorMessage() { return "The account associated with this key is inactive"; }
}
