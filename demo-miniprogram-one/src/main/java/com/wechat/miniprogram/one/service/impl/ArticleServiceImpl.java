package com.standard.demo.webapp.one.service.impl;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.standard.demo.core.config.base.BaseServiceImpl;
import com.standard.demo.web.core.entity.ResponseEntity;
import com.standard.demo.web.core.utils.UserUtils;
import com.standard.demo.webapp.one.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.standard.demo.webapp.one.dao.ArticleDao;
import com.standard.demo.webapp.one.entity.Article;
import com.standard.demo.webapp.one.service.ArticleService;
import com.standard.demo.web.core.utils.CommonUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author: zjw
 * @date: 2020/03/24 16:07
 */
@Service
@Slf4j
public class ArticleServiceImpl extends BaseServiceImpl<ArticleDao, Article> implements ArticleService {

	@Resource
	private ArticleDao articleDao;

	/**
	 * 新增文章
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity addArticle(Article jsonObject) {
		jsonObject.setCreateTime(LocalDateTime.now());
		jsonObject.setUpdateTime(LocalDateTime.now());
		jsonObject.setCreateBy(UserUtils.getCurrentUserId());
		jsonObject.setUpdateBy(UserUtils.getCurrentUserId());
		articleDao.addArticle(jsonObject);

		log.info("添加文章：{}成功", JSONObject.toJSONString(jsonObject));
		return CommonUtil.successJson();
	}

	/**
	 * 分页文章列表
	 */
	@Override
	public IPage<Article> listArticle(Article jsonObject) {
		LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		if (StringUtils.isNotBlank(jsonObject.getContent())) {
			lambdaQueryWrapper.like(Article::getContent, jsonObject.getContent());
		}

		Page<Article> page = new Page<>(jsonObject.getPageNo(), jsonObject.getPageSize());
		IPage<Article> pageResult = articleDao.selectPage(page, lambdaQueryWrapper);
		return pageResult;
	}

	/**
	 * 更新文章
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity updateArticle(Article jsonObject) {
		jsonObject.setUpdateBy(UserUtils.getCurrentUserId());
		jsonObject.setUpdateTime(LocalDateTime.now());
		articleDao.updateArticle(jsonObject);

		log.info("修改文章：{}成功", JSONObject.toJSONString(jsonObject));
		return CommonUtil.successJson();
	}
}
