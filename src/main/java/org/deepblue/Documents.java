package org.deepblue;

import java.util.ArrayList;
import java.util.List;

/**
 * A data class for a document list
 */
public final class Documents {

    /**
     * The document list
     */
    private List<Document> documents;

    /**
     * Builds an empty list of {@link Document}
     */
    public Documents() {
        documents = new ArrayList<>();
    }

    /**
     * Adds a document to the list in a thread safe way
     * @param doc the {@link Document}
     */
    public Documents add(Document doc) {
        synchronized (documents) {
            documents.add(doc);
        }
        return this;
    }

    public List<Document> getDocuments() {
        return new ArrayList<>(documents);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Documents)) return false;
        return documents.equals(((Documents) other).documents);
    }

    @Override
    public int hashCode() {
        return documents.hashCode();
    }

}
