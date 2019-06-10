package com.peter.bean;

public class Article {
    private Integer id;

    private String title;

    private String pmid;

    private String summary;

    public Article(Integer id, String title, String pmid) {
        this.id = id;
        this.title = title;
        this.pmid = pmid;
    }

    public Article(Integer id, String title, String pmid, String summary) {
        this.id = id;
        this.title = title;
        this.pmid = pmid;
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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }
}