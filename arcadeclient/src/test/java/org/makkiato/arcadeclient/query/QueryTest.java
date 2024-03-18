package org.makkiato.arcadeclient.query;

import static org.assertj.core.api.Assertions.assertThat;
import static org.makkiato.arcadeclient.query.Condition.And;
import static org.makkiato.arcadeclient.query.Condition.Equals;
import static org.makkiato.arcadeclient.query.Select.SelectFrom;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

public class QueryTest {
    @Test
    void simpleCondition() {
        var dateFormatter = DateTimeFormatter.ofPattern("dd MM yyyy GG");
        var date = LocalDate.parse("01 01 0001 BC", dateFormatter);
        var select = SelectFrom("Person").Where(And(Equals("name", "Friedrich"),
                Equals("dateOfBirth", date)));
        assertThat(select.toString()).isEqualTo(
                String.format("select from Person where (name = 'Friedrich' and dateOfBirth = %d))", date.toEpochDay()));
    }
}
