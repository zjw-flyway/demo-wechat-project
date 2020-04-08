package com.standard.demo.webapp.one.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.standard.demo.web.core.entity.ResponseEntity;
import com.standard.demo.webapp.one.entity.Article;

/**
 * @author: zjw
 * @date: 2020/03/24 16:06
 */
public interface ArticleService {
	/**
	 * 新增文章
	 */
	ResponseEntity addArticle(Article jsonObject);

	/**
	 * 文章列表
	 */
	IPage<Article> listArticle(Article jsonObject);

	/**
	 * 更新文章
	 */
	ResponseEntity updateArticle(Article jsonObject);
}
