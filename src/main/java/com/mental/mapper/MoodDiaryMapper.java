package com.mental.mapper;

import com.mental.entity.MoodDiary;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface MoodDiaryMapper {
    MoodDiary findById(@Param("id") Integer id);
    List<MoodDiary> findByUserId(@Param("userId") Integer userId);
    List<MoodDiary> findAll();
    int insert(MoodDiary diary);
    int deleteById(@Param("id") Integer id);
    int countByUserId(@Param("userId") Integer userId);
}
