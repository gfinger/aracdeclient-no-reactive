package org.makkiato.arcadeclient.base;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
@RequiredArgsConstructor
public class EmbeddedDocumentBase {
    private final String type;
    private final String cat;
}
