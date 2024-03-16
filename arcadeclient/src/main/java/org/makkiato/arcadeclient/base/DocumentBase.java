package org.makkiato.arcadeclient.base;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString
public class DocumentBase extends EmbeddedDocumentBase {
    public DocumentBase(String type, String cat, String rid) {
        super(type, cat);
        this.rid = rid;
    }

    @JsonProperty(value = "@rid", access = JsonProperty.Access.WRITE_ONLY)
    private final String rid;
}
