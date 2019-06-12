package com.peter.app;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.context.ApplicationContext;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.peter.bean.ArticleUrl;
import com.peter.parse.ArticleParser;
import com.peter.parse.ArticleUrlParser;
import com.peter.parse.InfoParser;
import com.peter.utils.CrawlConfig;

public class App {
	private static ApplicationContext context = null;
	private static List<String> userAgentList = null;
	private static List<String> keywords=null;
	private static String domain = "https://www.ncbi.nlm.nih.gov";

	private static void init() {
		context = CrawlConfig.getApplicationContext();
		userAgentList = CrawlConfig.getUserAgent();
		keywords=new ArrayList<String>();
		keywords.add("lncrna");
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {
		init();
		
		//爬取所有详情页连接
		/*InfoParser articleurlInfoParser=new ArticleUrlParser(context);
		articleurlInfoParser.parser();*/
		
		//爬取所有文章详情：标题、摘要、pmid
		InfoParser articleInfoParser=new ArticleParser(context);
		articleInfoParser.parser();
		

	}

	private static void getAllDetailUrl() throws FailingHttpStatusCodeException, IOException {

		final String base = "https://www.ncbi.nlm.nih.gov/pubmed/?term=";
		String keyword = keywords.get(0);
		String url = base + keyword;
		
		final WebClient webClient = new WebClient(BrowserVersion.CHROME);// 新建一个模拟谷歌Chrome浏览器的浏览器客户端对象

		webClient.getOptions().setThrowExceptionOnScriptError(false);// 当JS执行出错的时候是否抛出异常, 这里选择不需要
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);// 当HTTP的状态非200时是否抛出异常, 这里选择不需要
		webClient.getOptions().setActiveXNative(false);
		webClient.getOptions().setCssEnabled(false);// 是否启用CSS, 因为不需要展现页面, 所以不需要启用
		webClient.getOptions().setJavaScriptEnabled(true); // 很重要，启用JS
		webClient.getOptions().setTimeout(5000);
		webClient.getOptions().setDoNotTrackEnabled(false);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());// 很重要，设置支持AJAX

		WebRequest mainRequest = new WebRequest(new URL(url), HttpMethod.GET);
		mainRequest.setAdditionalHeader("user-agent", userAgentList.get(RandomUtils.nextInt(0, userAgentList.size())));
		HtmlPage page = webClient.getPage(mainRequest);
		webClient.waitForBackgroundJavaScript(3000);

		getAllDetails(page,keyword);

		HtmlInput pageMsg = page.querySelector("#pageno2");
		String lastPage = pageMsg.getAttribute("last");
		int last = Integer.parseInt(lastPage);
		HtmlElement nextBtn = page.querySelector("div.title_and_pager.bottom  a.active.page_link.next");
		System.out.println("last:"+last+"   nextbtn:"+nextBtn.asXml());
		for (int i = 0; i < last; i++) {
			HtmlPage nextPage = nextBtn.click();
			getAllDetails(nextPage,keyword);
		}

		webClient.close();
		System.out.println("ok");

	}

	private static void getAllDetails(HtmlPage page,String keyword) {
		DomNodeList<DomNode> allDetails = page.querySelectorAll("#maincontent > div > div > div > div.rslt > p > a");
		// ArticleUrlMapper articleUrlMapper = context.getBean(ArticleUrlMapper.class);
		ArticleUrl articleUrl = new ArticleUrl();

		for (DomNode detail : allDetails) {
			HtmlElement aElement = (HtmlElement) detail;
			String href = aElement.getAttribute("href");
			articleUrl.setId(null);
			articleUrl.setKeyword(keyword);
			articleUrl.setUrl(domain + href);
			System.out.println(articleUrl);
		}
	}
}
