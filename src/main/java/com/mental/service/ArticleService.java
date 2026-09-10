package com.mental.service;

import com.mental.entity.Article;
import com.mental.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    public Article findById(Integer id) {
        Article article = articleMapper.findById(id);
        if (article != null) {
            articleMapper.incrementViewCount(id);
        }
        return article;
    }

    public List<Article> findActive() {
        return articleMapper.findActive();
    }

    public List<Article> findAll() {
        return articleMapper.findAll();
    }

    public List<Article> findByCategory(String category) {
        return articleMapper.findByCategory(category);
    }

    public boolean save(Article article) {
        if (article.getId() == null) {
            if (article.getViewCount() == null) article.setViewCount(0);
            if (article.getStatus() == null) article.setStatus(1);
            return articleMapper.insert(article) > 0;
        } else {
            return articleMapper.update(article) > 0;
        }
    }

    public boolean delete(Integer id) {
        return articleMapper.deleteById(id) > 0;
    }

    public int countAll() {
        return articleMapper.countAll();
    }
}
