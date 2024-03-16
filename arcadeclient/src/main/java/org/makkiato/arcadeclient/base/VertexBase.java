package org.makkiato.arcadeclient.base;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString
public class VertexBase extends DocumentBase {
    public VertexBase(String type, String cat, String rid) {
        super(type, cat, rid);
    }
}
