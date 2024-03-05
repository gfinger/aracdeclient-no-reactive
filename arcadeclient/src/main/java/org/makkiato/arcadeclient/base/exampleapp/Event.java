package org.makkiato.arcadeclient.base.exampleapp;

import java.time.LocalDate;

import org.makkiato.arcadeclient.base.VertexBase;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Event extends VertexBase {
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
