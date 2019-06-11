package com.peter.bean;

public class ArticleUrl {
    private Integer id;

    private String url;

    private String keyword;

    public ArticleUrl(Integer id, String url, String keyword) {
        this.id = id;
        this.url = url;
        this.keyword = keyword;
    }

    public ArticleUrl() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword == null ? null : keyword.trim();
    }

	@Override
	public String toString() {
		return "ArticleUrl [id=" + id + ", url=" + url + ", keyword=" + keyword + "]";
	}
    
}