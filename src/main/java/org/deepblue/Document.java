package org.deepblue;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A data class for a Document
 */
public final class Document {

    private String id;

    private String text;

    private String language;

    private List<Language> detectedLaguages;

    private float score;

    private List<String> keyPhrases;

    /**
     * Constructs a Document
     * @param id doc's id
     * @param text doc's text
     */
    public Document(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public Document(String id, String text, String language) {
        this(id, text);
        this.language = language;
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
        return new ArrayList<>(detectedLaguages);
    }

    /**
     * Sentiment score given by azure backend
     */
    public float getScore() {
        return score;
    }

    /**
     * Keyphrases given by azure backend
     */
    public List<String> getKeyPhrases() {
        return new ArrayList<>(keyPhrases);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Document)) return false;
        Document doc = (Document) other;
        return Objects.equals(id, doc.id) &&
               Objects.equals(text, doc.text) &&
               Objects.equals(language, doc.language);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, language);
    }

}
