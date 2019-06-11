package com.peter.bean;

public class Article {
    private Integer id;

    private String title;

    private String pmid;

    private String url;

    private String keyword;

    private String summary;

    public Article(Integer id, String title, String pmid, String url, String keyword) {
        this.id = id;
        this.title = title;
        this.pmid = pmid;
        this.url = url;
        this.keyword = keyword;
    }

    public Article(Integer id, String title, String pmid, String url, String keyword, String summary) {
        this.id = id;
        this.title = title;
        this.pmid = pmid;
        this.url = url;
        this.keyword = keyword;
        this.summary = summary;
    }

    public Article() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getPmid() {
        return pmid;
    }

    public void setPmid(String pmid) {
        this.pmid = pmid == null ? null : pmid.trim();
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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }

	@Override
	public String toString() {
		return "Article [id=" + id + ", title=" + title + ", pmid=" + pmid + ", url=" + url + ", keyword=" + keyword
				+ ", summary=" + summary + "]";
	}
    
}