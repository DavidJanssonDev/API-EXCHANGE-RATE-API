package org.adapters.exchangerate.errors;

import org.core.interfaces.IRequestError;

public class QuotaReachedError implements IRequestError {
    @Override public String getErrorCode()    { return "quota-reached"; }
    @Override public String getErrorMessage() { return "Monthly request quota has been reached"; }
}
