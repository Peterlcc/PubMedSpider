package com.peter.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CrawlConfig {
	private static List<String> userAgentList=null;
	static {
		InputStream inputStream = CrawlConfig.class.getClassLoader().getResourceAsStream("user-agent.txt");
		try {
			userAgentList=IOUtils.readLines(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static List<String> getUserAgent() {
		return userAgentList;
	}
	private static ApplicationContext applicationContext=null;
	public static ApplicationContext getApplicationContext() {
		if (applicationContext==null) {
			synchronized(CrawlConfig.class)
			{
				if (applicationContext==null) {
					applicationContext=new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
				}
			}
		}
		return applicationContext;
	}
}
