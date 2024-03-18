package org.makkiato.arcadeclient.query;

import java.time.LocalDate;

public abstract class Condition {
    public abstract String toString();

    public static <T> Equals Equals(String fieldName, String value) {
        return new Equals(fieldName, value);
    }

    public static <T> Equals Equals(String fieldName, LocalDate date) {
        return new Equals(fieldName, date);
    }

    public static And And(Condition left, Condition right) {
        return new And(left, right);
    }
}
