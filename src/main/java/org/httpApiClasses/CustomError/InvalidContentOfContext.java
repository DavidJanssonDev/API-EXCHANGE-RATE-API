package org.httpApiClasses.CustomError;

public class InvalidContentOfContext extends RuntimeException {
    public InvalidContentOfContext(String msg) {
        super(msg);
    }
}
