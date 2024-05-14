package com.frnomad.pluginbasic.commands;

import java.util.regex.Pattern;

public enum ArgType {
    INTEGER,FLOAT,ALL;

    public boolean isValidForm(ArgType argtype, String arg) {
        if(argtype == ArgType.INTEGER) return Pattern.matches("^[0-9]*$", arg);
        if(argtype == ArgType.FLOAT) return Pattern.matches("^[0-9]+(|\\.[0-9]+)$", arg);
        else return true;
    }

    public String toKorean(ArgType argtype) {
        if(argtype == ArgType.INTEGER) return "정수";
        if(argtype == ArgType.FLOAT) return "실수";
        else return "";
    }
}


