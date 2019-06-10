package com.peter.utils;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.RandomUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class HttpClientRequest {

	private List<String> userAgentList = CrawlConfig.getUserAgent();
	private int randomIndex = 0;

	public HttpClientRequest() {
		randomIndex = RandomUtils.nextInt(0, userAgentList.size());
	}

	public String request(String url) {
		CloseableHttpClient client = null;
		CloseableHttpResponse response = null;
		try {
			client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(url);
			request.setHeader("user-agent", userAgentList.get(randomIndex));
			request.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
			request.setHeader("Accept-Language", "zh-cn,zh;q=0.9");
			request.setHeader("Connection", "keep-alive");
			request.setHeader("Accept-Charset", "  GB2312,utf-8;q=0.7,*;q=0.7");
			//System.out.print(" [user-agent:" + randomIndex + "] ");
			response = client.execute(request);
			//System.out.print(" response:" + response.getProtocolVersion() + " ");
			HttpEntity entity = response.getEntity();
			String text = EntityUtils.toString(entity, "utf-8");
			EntityUtils.consume(entity);
			//request.releaseConnection();
			return text;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {

			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//System.out.print("response closed ");
			if (client != null) {
				try {
					client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//System.out.print("client closed");
		}
	}
}
