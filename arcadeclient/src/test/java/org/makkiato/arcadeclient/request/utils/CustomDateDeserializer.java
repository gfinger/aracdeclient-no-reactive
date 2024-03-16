package org.makkiato.arcadeclient.request.utils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class CustomDateDeserializer extends JsonDeserializer<LocalDate> {

    @Override
    public LocalDate deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JacksonException {
        var dateFormatter = DateTimeFormatter.ofPattern("dd MM yyyy GG HH:mm:ss");
        var node = parser.getValueAsString();
        var date = LocalDate.parse(node, dateFormatter);
        return date;
    }

}
