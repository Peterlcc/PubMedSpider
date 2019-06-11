package com.peter.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;


import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;

public class HtmlUnitRequest  {

    private List<String> userAgentList=CrawlConfig.getUserAgent();
    private Map<String, String> headers=null;
    private Set<Cookie> cookies=null;
    
    public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public Set<Cookie> getCookies() {
		return cookies;
	}

	public void setCookies(Set<Cookie> cookies) {
		this.cookies = cookies;
	}

	public String request(String url) throws MalformedURLException {
    	final WebClient webClient = new WebClient(BrowserVersion.EDGE);//新建一个模拟谷歌Chrome浏览器的浏览器客户端对象

        webClient.getOptions().setThrowExceptionOnScriptError(false);//当JS执行出错的时候是否抛出异常, 这里选择不需要
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);//当HTTP的状态非200时是否抛出异常, 这里选择不需要
        webClient.getOptions().setActiveXNative(false);
        webClient.getOptions().setCssEnabled(false);//是否启用CSS, 因为不需要展现页面, 所以不需要启用
        webClient.getOptions().setJavaScriptEnabled(true); //很重要，启用JS
        webClient.getOptions().setTimeout(5000);
        webClient.getOptions().setDoNotTrackEnabled(false);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());//很重要，设置支持AJAX
        
        WebRequest request = new WebRequest(new URL(url), HttpMethod.POST);
        if (headers!=null) {
        	request.setAdditionalHeaders(headers);
		}
        if (cookies!=null) {
			for(Cookie cookie:cookies)
			{
				webClient.getCookieManager().addCookie(cookie);
			}
		}
        HtmlPage page = null;
        try {
            page = webClient.getPage(request);//尝试加载上面图片例子给出的网页
            webClient.waitForBackgroundJavaScript(3000);//异步JS执行需要耗时,所以这里线程要阻塞3秒,等待异步JS执行结束
            //Thread.sleep(10000);
            
            String pageXml = page.asXml();//直接将加载完成的页面转换成xml格式的字符串
            return pageXml;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            webClient.close();
        }

        
    }
}
