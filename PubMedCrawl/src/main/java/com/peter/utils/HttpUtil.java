package com.peter.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

public class HttpUtil {


    public static <T> T sendPost(String url, Object obj, TypeReference<T> type) throws Exception {
        try (
                CloseableHttpClient client = HttpClientBuilder.create().build();
        ) {
            HttpPost request = new HttpPost();
            request.setURI(new URI(url));
            request.addHeader("Content-Type", "application/json; charset=utf-8");
            request.setEntity(new StringEntity(JSON.toJSONString(obj)));

            HttpEntity entity = client.execute(request).getEntity();
            String json = IOUtils.toString(entity.getContent(), "utf-8");
            return JSON.parseObject(json, type);
        }
    }

    public static <T> T sendPost(String url, List<NameValuePair> params, TypeReference<T> type) throws Exception {
        try (
                CloseableHttpClient client = HttpClientBuilder.create().build();
        ) {
            HttpPost request = new HttpPost();
            request.setURI(new URI(url));
            request.setEntity(new UrlEncodedFormEntity(params, "utf-8"));

            HttpEntity entity = client.execute(request).getEntity();
            String json = IOUtils.toString(entity.getContent(), "utf-8");
            return JSON.parseObject(json, type);
        }
    }

    public static <T> T sendPost(String url, Map<String, ContentBody> params, TypeReference<T> type) throws Exception {
        try (
                CloseableHttpClient client = HttpClientBuilder.create().build();
        ) {
            MultipartEntityBuilder builder = MultipartEntityBuilder.create().setLaxMode();
            if (params != null) {
                for (String key : params.keySet()) {
                    ContentBody body = params.get(key);
                    builder.addPart(key, params.get(key));
                }
            }
            HttpPost request = new HttpPost();
            request.setURI(new URI(url));
            request.setEntity(builder.build());

            HttpEntity entity = client.execute(request).getEntity();
            String json = IOUtils.toString(entity.getContent(), "utf-8");
            return JSON.parseObject(json, type);
        }
    }

    public static <T> T sendGet(String url, List<NameValuePair> params, TypeReference<T> type) throws Exception {
        if (params != null) {
            url = url + "?" + URLEncodedUtils.format(params, "utf-8");
        }

        return sendGet(url, type);
    }

    public static <T> T sendGet(String url, TypeReference<T> type) throws Exception {
        try (
                CloseableHttpClient client = HttpClientBuilder.create().build();
        ) {
            StringBuilder builder = new StringBuilder(url);
            HttpGet request = new HttpGet(new URI(url));
            HttpEntity entity = client.execute(request).getEntity();
            String json = IOUtils.toString(entity.getContent(), "utf-8");
            return JSON.parseObject(json, type);
        }
    }

}
