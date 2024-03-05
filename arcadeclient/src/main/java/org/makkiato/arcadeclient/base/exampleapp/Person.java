package org.makkiato.arcadeclient.base.exampleapp;

import java.time.LocalDate;

import org.makkiato.arcadeclient.base.VertexBase;

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
        this.dateOfBirth = null;
    }
    
    private final String name;
    private final LocalDate dateOfBirth;
    private final LocalDate dateOfDeath;
}
