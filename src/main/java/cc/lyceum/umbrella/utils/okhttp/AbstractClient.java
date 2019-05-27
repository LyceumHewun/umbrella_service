package cc.lyceum.umbrella.utils.okhttp;

import java.io.IOException;
import java.util.Map;

public abstract class AbstractClient implements Client {

    @Override
    public String jsonPostToString(String url, String json, Map<String, String> headers) {
        try {
            return jsonPost(url, json, headers).body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String jsonPostToString(String url, String json) {
        return jsonPostToString(url, json, null);
    }

    @Override
    public String getToString(String url, Map<String, String> headers) {
        try {
            return get(url, headers).body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getToString(String url) {
        return getToString(url, null);
    }
}
