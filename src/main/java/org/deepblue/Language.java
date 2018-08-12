package org.deepblue;

import org.deepblue.util.StatisticalData;

import java.util.Objects;

/**
 * A data class for a language
 */
@SuppressWarnings("unused")
public final class Language extends StatisticalData {

    private String name;

    private String iso6391Name;

    private float score;

    /**
     * Language name
     */
    public String getName() {
        return name;
    }

    /**
     * Language ISO 6391 name
     */
    public String getIso6391Name() {
        return iso6391Name;
    }

    /**
     * Language score, how likely it is this language
     */
    public float getScore() {
        return score;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Language)) return false;
        Language l = (Language) other;
        return Objects.equals(name, l.name) &&
               Objects.equals(iso6391Name, l.iso6391Name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, iso6391Name);
    }

    @Override
    public String label() {
        return iso6391Name;
    }

    @Override
    public float value() {
        return 1;
    }

    @Override
    public float probability() {
        return score;
    }
}
