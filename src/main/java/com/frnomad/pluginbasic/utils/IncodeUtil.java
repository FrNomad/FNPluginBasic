package com.frnomad.pluginbasic.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IncodeUtil {

    private static final Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}]");

    public static String format(String msg) {
        Matcher match = pattern.matcher(msg);
        while(match.find()) {
            String color = msg.substring(match.start(), match.end());
            msg = msg.replace(color, net.md_5.bungee.api.ChatColor.of(color) + "");
            match = pattern.matcher(msg);
        }
        return net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static <T> T recoverNull(T value, T recov) {
        return value == null ? recov : value;
    }

    public static String replaceArguments(String oldString, String... arguments) {
        String newString = oldString;
        for(int n = 0; n < arguments.length; n++) {
            newString = newString.replaceAll("%" + (n + 1) + "%", arguments[n]);
        }
        return newString;
    }

}
