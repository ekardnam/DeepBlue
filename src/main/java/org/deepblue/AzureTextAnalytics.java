package org.deepblue;

import com.google.gson.*;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;

/**
 * Azure Text Analytics utility
 */
public final class AzureTextAnalytics {

    /**
     * The default azure backend the DeepBlue will connect to if not specified
     */
    public static final String DEFAULT_AZURE_URL = "https://westus.api.cognitive.microsoft.com";
    public static final String LANGUAGE_BACKEND = "/text/analytics/v2.0/languages";
    public static final String SENTIMENT_BACKEND = "/text/analytics/v2.0/sentiment";
    public static final String KEYWORDS_BACKEND = "/text/analytics/v2.0/keyPhrases";

    private String apiKey;
    private String url;

    /**
     * Creates an Azure Text Analytics REST API utility at DEFAULT_AZURE_URL
     * @param apiKey Your REST API key
     */
    public AzureTextAnalytics(String apiKey) {
        this(apiKey, DEFAULT_AZURE_URL);
    }

    /**
     * Creates an Azure Text Analytics REST API utility
     * @param apiKey Your API key
     * @param url URL the azure backend is at (you might want to change depending on the area you registered you API key)
     */
    public AzureTextAnalytics(String apiKey, String url) {
        this.apiKey = apiKey;
        this.url = url;
    }

    /**
     * Gets the language of the given documents
     * @param documents the {@link Documents}
     * @return a {@link Documents} object with attached languages in the {@link Document#getDetectedLaguages()}
     * @throws IOException
     */
    public Documents getLanguage(Documents documents) throws IOException {
        return requestDataFromBackend(LANGUAGE_BACKEND, documents);
    }

    /**
     * Gets the sentiment score of the given documents
     * @param documents the {@link Documents}
     * @return a {@link Documents} object with attached score in {@link Document#getScore()}
     * @throws IOException
     */
    public Documents getSentiment(Documents documents) throws IOException {
        return requestDataFromBackend(SENTIMENT_BACKEND, documents);
    }

    /**
     * Gets the keywords of the given documents
     * @param documents the {@link Documents}
     * @return a {@link Documents} object with attached keywords in {@link Document#getKeyPhrases()}
     * @throws IOException
     */
    public Documents getKeywords(Documents documents) throws IOException {
        return requestDataFromBackend(KEYWORDS_BACKEND, documents);
    }

    private Documents requestDataFromBackend(String path, Documents documents) throws IOException {
        byte[] jsonData = encode(documents);
        HttpsURLConnection connection = connectToAzureBackend(path);
        writeBytes(jsonData, connection.getOutputStream());
        String reply = readReply(connection.getInputStream());

        return new Gson().fromJson(reply, Documents.class);
    }

    private void writeBytes(byte[] bytes, OutputStream os) throws IOException {
        try (DataOutputStream writer = new DataOutputStream(os)) {
            writer.write(bytes, 0, bytes.length);
            writer.flush();
        }
    }

    private String readReply(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        return sb.toString();
    }

    private byte[] encode(Documents documents) throws UnsupportedEncodingException {
        Gson gson = new Gson();
        String json = gson.toJson(documents);
        byte[] jsonEncoded = json.getBytes("UTF-8");

        return jsonEncoded;
    }

    private HttpsURLConnection connectToAzureBackend(String path) throws IOException {
        URL backend = new URL(url + path);
        HttpsURLConnection connection = (HttpsURLConnection) backend.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "text/json");
        connection.setRequestProperty("Ocp-Apim-Subscription-Key", apiKey);
        connection.setDoOutput(true);

        return connection;
    }

}
