package com.standard.demo.webapp.one.dao;

import com.standard.demo.core.config.base.BaseDataMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.standard.demo.webapp.one.entity.Article;

/**
 * @author: zjw
 * @description: 文章Dao层
 * @date: 2020/03/24 16:06
 */
public interface ArticleDao extends BaseDataMapper<Article> {

    /**
     * 新增文章
     */
    @Insert("insert into article (content,create_time,update_time,create_by,update_by,del_flag) values ("
        + "#{article.content},#{article.createTime},#{article.updateTime},#{article.createBy},#{article.updateBy},1)")
    int addArticle(@Param("article") Article article);

    /**
     * 更新文章
     */
    @Update("update article set content=#{article.content},update_by=#{article.updateBy},"
        + "update_time=now() where id=#{article.id} ")
    int updateArticle(@Param("article") Article article);
}
