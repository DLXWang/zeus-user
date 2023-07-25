package com.xinxu.user.util;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class MapMessage extends LinkedHashMap<String, Object> {
    private static final long serialVersionUID = -5410799397055048515L;

    private static final String __duplicatedOperation = "__duplicatedOperation";

    // ========================================================================
    // success
    // ========================================================================

    public MapMessage setSuccess(Boolean v) {
        put("success", v);
        return this;
    }

    @JsonIgnore
    public boolean isSuccess() {
        Object value = get("success");
        return SafeConverter.toBoolean(value);
    }


    @JsonIgnore
    @Deprecated
    public boolean getSuccess() {
        return isSuccess();
    }

    // ========================================================================
    // info
    // ========================================================================

    @JsonIgnore
    public MapMessage setInfo(String s) {
        put("info", s);
        return this;
    }

    @JsonIgnore
    public String getInfo() {
        Object value = get("info");
        if (value == null) {
            return "";
        }
        if (!(value instanceof String)) {
            return "";
        }
        return (String) value;
    }

    // ========================================================================
    // errorUrl and errorCode
    // ========================================================================

    public MapMessage setErrorUrl(String s) {
        put("errorUrl", s);
        return this;
    }

    @JsonIgnore
    public String getErrorUrl() {
        Object value = get("errorUrl");
        if (value == null) {
            return "";
        }
        if (!(value instanceof String)) {
            return "";
        }
        return (String) value;
    }

    public void clearErrorUrl() {
        remove("errorUrl");
    }

    public MapMessage setErrorCode(String s) {
        put("errorCode", s);
        return this;
    }

    @JsonIgnore
    public String getErrorCode() {
        Object value = get("errorCode");
        if (value == null) {
            return "";
        }
        if (!(value instanceof String)) {
            return "";
        }
        return (String) value;
    }

    public void clearErrorCode() {
        remove("errorCode");
    }

    // ========================================================================
    // value
    // ========================================================================

    @Deprecated
    public MapMessage setValue(Object v) {
        put("value", v);
        return this;
    }

    @JsonIgnore
    @Deprecated
    public Object getValue() {
        return get("value");
    }

    // ========================================================================
    // attributes
    // ========================================================================


    @JsonIgnore
    @Deprecated
    public Map getAttributes() {
        Map m = (Map) get("attributes");
        if (m == null) {
            m = new LinkedHashMap();
            put("attributes", m);
        }
        return m;
    }


    @Deprecated
    public void setAttributes(Map m) {
        put("attributes", m);
    }

    // ========================================================================
    // duplicatedException
    // ========================================================================

    public MapMessage withDuplicatedException() {
        return add(__duplicatedOperation, Boolean.TRUE);
    }

    @JsonIgnore
    public boolean hasDuplicatedException() {
        return containsKey(__duplicatedOperation);
    }


    // ========================================================================
    // add & set
    // ========================================================================

    public MapMessage add(String k, Object v) {
        Object last = super.put(k, v);
        if (last != null) {
            throw new IllegalArgumentException("key " + k + " already exists in map, overwritten by new value");
        }
        return this;
    }

    public MapMessage set(String k, Object v) {
        super.put(k, v);
        return this;
    }

    // ========================================================================
    // json
    // ========================================================================
    public String toJson() {
        return JsonUtils.toJson(this);
    }

    // ========================================================================
    // static factory methods
    // ========================================================================

    public static MapMessage of(Map<String, Object> map) {
        if (map == null) {
            return null;
        }
        MapMessage message = new MapMessage();
        message.putAll(map);
        return message;
    }

    public static MapMessage successMessage() {
        return new MapMessage().setSuccess(true);
    }

    public static MapMessage successMessage(String info, Object... args) {
        info = ExtendedStringUtils.formatMessage(info, args);
        return new MapMessage().setSuccess(true).setInfo(info);
    }

    public MapMessage of(Object... args) {
        putAll(map(args));
        return this;
    }

    static public <K, V> Map<K, V> map(Object... args) {
        if (args == null || args.length % 2 != 0) {
            throw new IllegalArgumentException();
        }
        Map<K, V> m = new HashMap<>(args.length / 2);
        for (int i = 0; i < args.length; i += 2) {
            m.put((K) args[i], (V) args[i + 1]);
        }
        return m;
    }

    public static MapMessage errorMessage() {
        return new MapMessage().setSuccess(false);
    }

    public static MapMessage errorMessage(String info, Object... args) {
        info = ExtendedStringUtils.formatMessage(info, args);
        return new MapMessage().setSuccess(false).setInfo(info);
    }

}
