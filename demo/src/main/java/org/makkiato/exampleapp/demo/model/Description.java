package org.makkiato.arcadeclient.base.exampleapp;

import org.makkiato.arcadeclient.base.DocumentBase;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Description extends DocumentBase{
    private final String text;

    public Description(String type, String cat, String rid, String text) {
        super(type, cat, rid);
        this.text = text;
    }
}
