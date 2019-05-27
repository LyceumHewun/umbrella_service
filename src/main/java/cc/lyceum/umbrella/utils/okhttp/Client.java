package cc.lyceum.umbrella.utils.okhttp;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用于发送网络请求
 */
public interface Client {

    Response jsonPost(String url, String json, Map<String, String> headers) throws IOException;

    String jsonPostToString(String url, String json, Map<String, String> headers);

    String jsonPostToString(String url, String json);

    Response get(String url, Map<String, String> headers) throws IOException;

    String getToString(String url, Map<String, String> headers);

    String getToString(String url);

    /**
     * okhttp的cookieJar, 用于管理请求中的cookie
     * <p>
     * 这里是每次请求有新的返回cookie就添加到cookieStore
     *
     * @param cookieStore cookieStore
     * @return CookieJar
     */
    default CookieJar newCookieJar(ConcurrentHashMap<String, List<Cookie>> cookieStore) {
        return new CookieJar() {
            @Override
            public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
                list = new ArrayList<>(list);
                if (!cookieStore.isEmpty()) {
                    for (Cookie cookie1 : cookieStore.get(httpUrl.host())) {
                        boolean repeat = false;
                        for (Cookie cookie2 : list) {
                            if (Objects.equals(cookie1.name(), cookie2.name())) {
                                repeat = true;
                                break;
                            }
                        }
                        if (!repeat) {
                            list.add(cookie1);
                        }
                    }
                }
                cookieStore.put(httpUrl.host(), list);
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl httpUrl) {
                List<Cookie> cookies = cookieStore.get(httpUrl.host());
                return cookies != null ? cookies : new ArrayList<>();
            }
        };
    }
}
