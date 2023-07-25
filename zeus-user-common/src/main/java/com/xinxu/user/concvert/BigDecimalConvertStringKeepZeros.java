package com.xinxu.user.concvert;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;

public class BigDecimalConvertStringKeepZeros extends JsonSerializer<BigDecimal> {
    @Override
    public void serialize(BigDecimal arg0, JsonGenerator arg1, SerializerProvider arg2) throws IOException {
        if (arg0 != null) {
            arg1.writeString(arg0.toPlainString());
        }
    }
}
