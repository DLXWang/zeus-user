package com.xinxu.user.util;

import org.apache.commons.lang3.StringUtils;

class ExtendedStringUtils extends StringUtils{
    public static final String DELIM = "{}";

    static String formatMessage(String messagePattern, Object... params) {
        if (!contains(messagePattern, DELIM)) {
            return messagePattern;
        }
        if (params == null || params.length == 0) {
            return messagePattern;
        }

        String message = messagePattern;
        String[] replacementList = new String[params.length];
        for (int i = 0; i < replacementList.length; i++) {
            replacementList[i] = String.valueOf(params[i]);
        }
        for (String replacement : replacementList) {
            message = replaceOnce(message, DELIM, replacement);
        }
        return message;
    }
}
