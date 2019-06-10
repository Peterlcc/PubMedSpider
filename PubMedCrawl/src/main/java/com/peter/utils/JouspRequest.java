package com.peter.utils;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomUtils;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JouspRequest {

	private List<String> userAgentList = CrawlConfig.getUserAgent();
	private int randomIndex = 0;
	private boolean ishtml=false;

	public JouspRequest() {
		randomIndex = RandomUtils.nextInt(0, userAgentList.size());
	}
	
	public String text(String url) {
		Connection connect = Jsoup.connect(url);
		Map<String, String> requestHead = new HashMap<String, String>();
		requestHead.put("User-Agent", userAgentList.get(randomIndex));
		
		requestHead.put("Accept-Language", "zh-cn,zh;q=0.5");
		requestHead.put("Accept-Charset", "  GB2312,utf-8;q=0.7,*;q=0.7");
		requestHead.put("Connection", "keep-alive");
		connect.headers(requestHead);
		try {
			return connect.ignoreContentType(true).get().text();
		}catch (SocketTimeoutException e) {
			System.out.println("SocketTimeoutException");
			return null;
		}
		catch (IOException e) {
			e.printStackTrace();
			System.out.println("IOException");
			return null;
		}
	}
	
	public Document request(String url) {
		Connection connect = Jsoup.connect(url);
		Map<String, String> requestHead = new HashMap<String, String>();
		requestHead.put("User-Agent", userAgentList.get(randomIndex));
		requestHead.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
		requestHead.put("Accept-Language", "zh-cn,zh;q=0.5");
		requestHead.put("Accept-Charset", "  GB2312,utf-8;q=0.7,*;q=0.7");
		requestHead.put("Connection", "keep-alive");
		connect.headers(requestHead);
		try {
			return connect.get();
		}catch (SocketTimeoutException e) {
			System.out.println("SocketTimeoutException");
			return null;
		}
		catch (IOException e) {
			System.out.println("IOException");
			return null;
		}
	}
	
}
