package com.xinxu.user.util;


import com.xinxu.user.constant.SignatureHeader;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.Map;

public class TokenUtils {

    public static Map<String, Object> parse(String token) {
        String[] split = token.split("\\.");
        byte[] decodedBytes = Base64.getDecoder().decode(split[1]);
        String decodedString = new String(decodedBytes, StandardCharsets.UTF_8);
        return JsonUtils.fromJson(decodedString);
    }


    public static boolean isTokenExpired(String token) {
        long epochSecond = Instant.now().getEpochSecond();
        Map<String, Object> parse = parse(token);
        Integer exp = (Integer) parse.get(SignatureHeader.TOKEN_EXP);
        return  exp < epochSecond;

    }

    public static boolean isNotTokenExpired(String token) {
        return !isTokenExpired(token);
    }

}
