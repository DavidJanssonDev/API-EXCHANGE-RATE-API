package org.httpApiClasses.CustomError;

public class InvalidContentOfContext extends Exception {
    public InvalidContentOfContext(String msg) {
        super(msg);
    }
}
