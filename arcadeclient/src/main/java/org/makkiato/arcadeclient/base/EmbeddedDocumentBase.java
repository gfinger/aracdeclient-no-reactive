package org.makkiato.arcadeclient.base;

import org.springframework.core.annotation.AnnotationUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class EmbeddedDocumentBase {
    @JsonProperty(value = "@type")
    private final String type;
    @JsonProperty(value = "@cat", access = JsonProperty.Access.WRITE_ONLY)
    private final String cat;

    public EmbeddedDocumentBase(String type, String cat) {
        var annotatedName = AnnotationUtils.getAnnotation(this.getClass(), Document.class);
        var documentType = annotatedName != null && annotatedName.value() != null && !annotatedName.value().isEmpty()
                ? annotatedName.value()
                : this.getClass().getSimpleName();
        this.type = type != null ? type : documentType;
        this.cat = cat;
    }
}
