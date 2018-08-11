package org.deepblue;

import java.io.IOException;
import java.util.List;

/**
 * A wrapper class to make API calls faster
 */
public final class DeepBlue {

    private AzureTextAnalytics azureBackend;

    /**
     * Contructs the wrapper, needs and {@link AzureTextAnalytics} backend
     * @param azureBackend the backend
     */
    public DeepBlue(AzureTextAnalytics azureBackend) {
        this.azureBackend = azureBackend;
    }

    /**
     * Get the predicted languages of the given text
     * @param text the text
     * @return a List of Language of languages
     * @throws IOException connection went wrong
     */
    public List<Language> getLanguages(String text) throws IOException {
        return getReplyDocument(text).getDetectedLaguages();
    }

    /**
     * Gets the sentiment score of the given text
     * @param text the text
     * @param langIsoName the ISO name of the language the text is written in (en, es, it)
     * @return a float between 0 and 1 corresponding to the sentiment predicted
     * @throws IOException the connection went wrong
     */
    public float getSentiment(String text, String langIsoName) throws IOException {
        return getReplyDocumentWithLang(text, langIsoName).getScore();
    }

    /**
     * Gets the keywords of the given text
     * @param text the text
     * @param langIsoName the language ISO name (en, es, it)
     * @return a List of String with the keywords
     * @throws IOException connection went wrong
     */
    public List<String> getKeywords(String text, String langIsoName) throws IOException {
        return getReplyDocumentWithLang(text, langIsoName).getKeyPhrases();
    }

    private Document getReplyDocument(String text) throws IOException {
        Documents docs = new Documents().add(new Document("1", text));
        Documents reply = azureBackend.getLanguage(docs);
        Document replyDoc = reply.getDocuments().get(0);
        return replyDoc;
    }

    private Document getReplyDocumentWithLang(String text, String langIsoName) throws IOException {
        Documents docs = new Documents().add(new Document("1", text, langIsoName));
        Documents reply = azureBackend.getLanguage(docs);
        Document replyDoc = reply.getDocuments().get(0);
        return replyDoc;
    }

}
