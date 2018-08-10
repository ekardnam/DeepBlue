package org.deepblue;

import java.io.IOException;
import java.util.List;

public final class DeepBlue {

    private AzureTextAnalytics azureBackend;

    public DeepBlue(AzureTextAnalytics azureBackend) {
        this.azureBackend = azureBackend;
    }

    public List<Language> getLanguages(String text) throws IOException {
        return getReplyDocument(text).getDetectedLaguages();
    }

    public float getSentiment(String text, String langIsoName) throws IOException {
        return getReplyDocumentWithLang(text, langIsoName).getScore();
    }

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
