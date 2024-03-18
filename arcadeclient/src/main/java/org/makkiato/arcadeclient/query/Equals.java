package org.makkiato.arcadeclient.query;

import java.time.LocalDate;

public class Equals extends Condition {
    private final String stringValue;

    public Equals(String fieldName, String value) {
        this.stringValue = String.format("%s = '%s'", fieldName, value.toString());
    }

    public Equals(String fieldName, LocalDate date) {
        this.stringValue = String.format("%s = %d", fieldName, date.toEpochDay());
    }

    @Override
    public String toString() {
        return stringValue;
    }

}
