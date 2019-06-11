package com.peter.mapper;

import com.peter.bean.ArticleUrl;

public interface ArticleUrlMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ArticleUrl record);

    int insertSelective(ArticleUrl record);

    ArticleUrl selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ArticleUrl record);

    int updateByPrimaryKey(ArticleUrl record);
    
    int selectTotalRecord();
    
    int truncateTable();
}