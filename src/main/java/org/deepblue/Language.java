package org.deepblue;

/**
 * A data class for a language
 */
public class Language {

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
}
