package com.frnomad.pluginbasic.commands.exceptions;

import com.frnomad.pluginbasic.commands.ArgType;

public class WrongFormatException extends RuntimeException {

    private ArgType type;

    public WrongFormatException(ArgType type) {
        super();
        this.type = type;
    }

    public ArgType getReason() {
        return this.type;
    }

}
