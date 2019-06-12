package com.peter.parse;

import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.context.ApplicationContext;

import com.peter.bean.Article;
import com.peter.bean.ArticleUrl;
import com.peter.mapper.ArticleMapper;
import com.peter.mapper.ArticleUrlMapper;
import com.peter.utils.HttpClientRequest;

public class ArticleParser extends InfoParser {
	private ArticleUrlMapper articleUrlMapper = null;
	private ArticleMapper articleMapper = null;

	public ArticleParser(ApplicationContext context) {
		super(context);
		articleUrlMapper = context.getBean(ArticleUrlMapper.class);
		articleMapper = context.getBean(ArticleMapper.class);
	}

	@Override
	public void parser() {
		int record = articleUrlMapper.selectTotalRecord();
		Article article = new Article();
		for (int i = 1; i <= record; i++) {
			ArticleUrl articleUrl = articleUrlMapper.selectByPrimaryKey(i);
			getArticleFromUrl(articleUrl, article);
			articleMapper.insertSelective(article);
		}

	}

	private void getArticleFromUrl(ArticleUrl articleUrl, Article article) {
		article.setId(null);
		article.setKeyword(articleUrl.getKeyword());
		article.setUrl(articleUrl.getUrl());

		HttpClientRequest request = new HttpClientRequest();
		String html = request.request(articleUrl.getUrl());
		Document doc = Jsoup.parse(html);
		
		Element titleElement = doc.selectFirst("#maincontent > div > div.rprt_all > div > h1");
		article.setTitle(titleElement.text());
		
		Element summaryElement = doc.selectFirst("#maincontent > div > div.rprt_all > div > div.abstr > div");
		article.setSummary(summaryElement.text());
		
		Element pmidElement = doc.selectFirst("#maincontent > div > div.rprt_all > div > div.aux > div> dl > dd");
		article.setPmid(pmidElement.text());
		System.out.println("第"+articleUrl.getId()+"个详情页");
	}

}
