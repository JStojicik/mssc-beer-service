package com.springframework.msscbeerservice.web.model;

import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ser.std.StdSerializer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateSerializer extends StdSerializer<LocalDate> {

    public LocalDateSerializer() {
        super(LocalDate.class);
    }

    @Override
    public void serialize(LocalDate value, JsonGenerator jsonGenerator,
                          SerializationContext ctxt) {
        jsonGenerator.writeString(value.format(DateTimeFormatter.BASIC_ISO_DATE));
    }
}
