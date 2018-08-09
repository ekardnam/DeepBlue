package org.deepblue;

import org.jetbrains.annotations.Contract;

import java.util.List;

/**
 * A data class for a Document
 */
public final class Document {

    private String id;

    private String text;

    private String language;

    private List<Language> detectedLaguages;

    private float score;

    /**
     * Constructs a Document
     * @param id doc's id
     * @param text doc's text
     */
    public Document(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public Document(String id, String language, String text) {
        this(id, text);
        setLanguage(language);
    }

    /**
     * The document's id
     */
    public String getId() {
        return id;
    }

    /**
     * The document's text
     */
    public String getText() {
        return text;
    }

    /**
     * Languages detected by azure backend
     */
    public List<Language> getDetectedLaguages() {
        return detectedLaguages;
    }

    /**
     * Sentiment score given by azure backend
     */
    public float getScore() {
        return score;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
