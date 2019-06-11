package com.peter.mapper;

import java.util.List;

import com.peter.bean.Condition;

public interface ConditionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Condition record);

    int insertSelective(Condition record);

    Condition selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Condition record);

    int updateByPrimaryKey(Condition record);
    
    
    List<Condition> selectAll();
}