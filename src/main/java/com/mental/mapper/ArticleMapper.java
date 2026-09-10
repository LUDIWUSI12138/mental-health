package com.mental.mapper;

import com.mental.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ArticleMapper {
    Article findById(@Param("id") Integer id);
    List<Article> findAll();
    List<Article> findByCategory(@Param("category") String category);
    List<Article> findActive();
    int insert(Article article);
    int update(Article article);
    int deleteById(@Param("id") Integer id);
    int countAll();
    int incrementViewCount(@Param("id") Integer id);
}
