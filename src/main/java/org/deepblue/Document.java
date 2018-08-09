package org.deepblue;

import java.util.List;

/**
 * A data class for a Document
 */
public class Document {

    /**
     * The document's id
     */
    private String id;

    /**
     * The document's text
     */
    private String text;

    /**
     * Languages detected by azure backend
     */
    private List<Language> detectedLaguages;

    /**
     * Constructs a Document
     * @param id doc's id
     * @param text doc's text
     */
    public Document(String id, String text) {
        this.id = id;
        this.text = text;
    }

}
