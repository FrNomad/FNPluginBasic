package com.frnomad.pluginbasic.commands.exceptions;

public class NoPermissionException extends RuntimeException {

    private ExceptionReason reason;

    public NoPermissionException(ExceptionReason reason) {
        super();
        this.reason = reason;
    }

    public ExceptionReason getReason() {
        return this.reason;
    }

}
