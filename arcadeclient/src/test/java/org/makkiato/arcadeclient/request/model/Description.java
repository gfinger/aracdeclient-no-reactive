package org.makkiato.arcadeclient.request.model;

import org.makkiato.arcadeclient.base.EmbeddedDocumentBase;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Description extends EmbeddedDocumentBase{
    private final String text;

    public Description(String type, String cat, String text) {
        super(type, cat);
        this.text = text;
    }
}
