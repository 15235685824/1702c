package com.bawei.cms.service;

import java.util.LinkedHashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bawei.cms.core.Page;
import com.bawei.cms.domain.Article;
import com.bawei.cms.domain.User;

/**
 * 说明:
 * 
 * @author howsun ->[howsun.zhang@gmail.com]
 * @version 1.0
 *
 * 2019年3月28日 下午4:48:47
 */
public interface ArticleService {

	/**
	 * 功能说明：<br>
	 * @param conditions
	 * @param page
	 * @param orders  排序，默认按创建时间倒排序
	 * @return
	 * List<Article>
	 */
	public abstract List<Article> gets(Article conditions, Page page, LinkedHashMap<String, Boolean> orders);
	
	
	public List<Article> selects( Article article, LinkedHashMap<String, Boolean> orders,Page page);
	
	
	
	/**
	 * 功能说明：统计<br>
	 * @param article
	 * @return
	 * int
	 */
	public int count(@Param("article") Article article);
	
	//根据主键查询
	public Article selectByPrimaryKey(Integer id);
	
	//根据id 更新
	public int updateByKey(Article article);
	
	void saveBlog(Article article);


	public abstract void remove(Integer id);


	public abstract List<Article> queryAll(Article article);


	public abstract void increaseHit(Integer id);


	public abstract Article queryWithTerm(Integer id);
}