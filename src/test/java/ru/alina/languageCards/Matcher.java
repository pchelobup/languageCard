package ru.alina.languageCards;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class Matcher {
    public static<T> void match(T actual, T expected, String...ignore) {
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields(ignore)
                .isEqualTo(expected);
    }

    public static<T> void match(Collection<T> actual, Collection<T> expected, String...ignore) {
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields(ignore)
                .isEqualTo(expected);
    }
}
