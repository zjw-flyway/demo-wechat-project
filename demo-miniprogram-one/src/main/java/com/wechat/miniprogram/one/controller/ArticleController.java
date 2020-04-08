package com.standard.demo.webapp.one.controller;

import javax.annotation.Resource;

import com.standard.demo.web.core.entity.ResponseEntity;
import com.standard.demo.web.core.utils.CommonUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.standard.demo.webapp.one.entity.Article;
import com.standard.demo.webapp.one.service.ArticleService;

import io.swagger.annotations.Api;

/**
 * @author: zjw
 * @description: 文章相关Controller
 * @date: 2020/03/23 16:04
 */
@RestController
@RequestMapping("/article")
@Api(tags = "文章")
public class ArticleController {

	@Resource
	private ArticleService articleService;

	/**
	 * 查询文章列表
	 */
	@RequiresPermissions("article:list")
	@GetMapping("/listArticle")
	@ApiOperation("查询文章信息")
	public ResponseEntity listArticle(Article article) {
		return CommonUtil.successJson(articleService.listArticle(article));
	}

	/**
	 * 新增文章
	 */
	@RequiresPermissions("article:add")
	@PostMapping("/addArticle")
	@ApiOperation("新增文章")
	public ResponseEntity addArticle(@RequestBody Article requestJson) {
		return articleService.addArticle(requestJson);
	}

	/**
	 * 修改文章
	 */
	@RequiresPermissions("article:update")
	@PostMapping("/updateArticle")
	@ApiOperation("修改文章")
	public ResponseEntity updateArticle(@RequestBody Article requestJson) {
		return articleService.updateArticle(requestJson);
	}
}
