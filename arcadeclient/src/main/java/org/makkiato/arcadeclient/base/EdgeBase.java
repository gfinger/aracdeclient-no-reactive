package org.makkiato.arcadeclient.base;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString
public class EdgeBase extends DocumentBase{
    public EdgeBase(String type, String cat, String rid, String in, String out) {
        super(type, cat, rid);
        this.in = in;
        this.out = out;
    }
    @JsonProperty(value = "@in", access = JsonProperty.Access.WRITE_ONLY)
    private final String in;
    @JsonProperty(value = "@out", access = JsonProperty.Access.WRITE_ONLY)
    private final String out;
}
