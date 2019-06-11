package com.peter.parse;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.context.ApplicationContext;

import com.gargoylesoftware.htmlunit.BrowserVersion;
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
import com.peter.bean.Condition;
import com.peter.mapper.ArticleUrlMapper;
import com.peter.mapper.ConditionMapper;
import com.peter.utils.CrawlConfig;

public class ArticleUrlParser extends InfoParser {
	private final String domain = "https://www.ncbi.nlm.nih.gov";
	final String base = "https://www.ncbi.nlm.nih.gov/pubmed/?term=";

	private List<Condition> conditions = null;
	private List<String> userAgentList = null;
	private ArticleUrlMapper articleUrlMapper = null;

	private void init() {
		context = CrawlConfig.getApplicationContext();
		userAgentList = CrawlConfig.getUserAgent();
		ConditionMapper conditionMapper = context.getBean(ConditionMapper.class);
		conditions = conditionMapper.selectAll();
		articleUrlMapper = context.getBean(ArticleUrlMapper.class);
	}

	public ArticleUrlParser(ApplicationContext context) {
		super(context);
		init();
	}

	@Override
	public void parser() {
		for (Condition condition : conditions) {
			String keyword = condition.getItem();
			try {
				getAllPagesFromCondition(keyword);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}

	}

	private void getAllPagesFromCondition(String keyword) throws IOException {
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

		URL requestUrl = null;
		requestUrl = new URL(url);

		WebRequest mainRequest = new WebRequest(requestUrl, HttpMethod.GET);
		mainRequest.setAdditionalHeader("user-agent", userAgentList.get(RandomUtils.nextInt(0, userAgentList.size())));
		HtmlPage page = null;
		page = webClient.getPage(mainRequest);

		webClient.waitForBackgroundJavaScript(3000);

		getAllDetails(page, keyword);

		HtmlInput pageMsg = page.querySelector("#pageno2");
		String lastPage = pageMsg.getAttribute("last");
		int last = Integer.parseInt(lastPage);
		HtmlElement nextBtn = page.querySelector("div.title_and_pager.bottom  a.active.page_link.next");

		for (int i = 0; i < last; i++) {
			System.out.println("关键字：" + keyword + "--第" + i + "页：");
			HtmlPage nextPage = null;
			nextPage = nextBtn.click();
			getAllDetails(nextPage, keyword);
		}

		webClient.close();
		System.out.println("爬取结束");
	}

	private void getAllDetails(HtmlPage page, String keyword) {
		DomNodeList<DomNode> allDetails = page.querySelectorAll("#maincontent > div > div > div > div.rslt > p > a");
		ArticleUrl articleUrl = new ArticleUrl();
		int count = 0;
		for (DomNode detail : allDetails) {
			HtmlElement aElement = (HtmlElement) detail;
			String href = aElement.getAttribute("href");
			articleUrl.setId(null);
			articleUrl.setKeyword(keyword);
			articleUrl.setUrl(domain + href);
			articleUrlMapper.insertSelective(articleUrl);
			count++;
		}
		System.out.println("共"+count+"个");
	}

}
