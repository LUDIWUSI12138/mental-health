package com.mental.mapper;

import com.mental.entity.Announcement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface AnnouncementMapper {
    Announcement findById(@Param("id") Integer id);
    List<Announcement> findActive();
    List<Announcement> findAll();
    int insert(Announcement announcement);
    int update(Announcement announcement);
    int deleteById(@Param("id") Integer id);
}
