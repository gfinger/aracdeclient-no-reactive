package org.makkiato.arcadeclient.request.model;

import java.time.LocalDate;

import org.makkiato.arcadeclient.base.VertexBase;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers.DateDeserializer;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Event extends VertexBase {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd MM yyyy GG")
    @JsonDeserialize(using = DateDeserializer.class)
    private final LocalDate date;

    private final String title;

    private Description description;

    public Event(String type, String cat, String rid, LocalDate date, String title, final Description description) {
        super(type, cat, rid);
        this.date = date;
        this.title = title;
        this.description = description;
    }
}
