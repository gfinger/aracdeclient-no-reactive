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
public class Person extends VertexBase {
    public Person(String type, String cat, String rid, String name, LocalDate dateOfBirth, LocalDate dateOfDeath) {
        super(type, cat, rid);
        this.name = name;
        this.dateOfDeath = dateOfDeath;
        this.dateOfBirth = dateOfBirth;
    }

    private final String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd MM yyyy GG")
    @JsonDeserialize(using = DateDeserializer.class)
    private final LocalDate dateOfBirth;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd MM yyyy GG")
    @JsonDeserialize(using = DateDeserializer.class)
    private final LocalDate dateOfDeath;
}
