package com.xinxu.user.concvert;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class LongConvertStringJsonSerializer extends JsonSerializer<Long> {
    @Override
    public void serialize(Long arg0, JsonGenerator arg1, SerializerProvider arg2) throws IOException {
        if (arg0 != null) {
            arg1.writeString(String.valueOf(arg0));
        }
    }
}
