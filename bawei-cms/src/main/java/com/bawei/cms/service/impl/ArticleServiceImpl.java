/**
 * 
 */
package com.bawei.cms.service.impl;

import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bawei.cms.core.Page;
import com.bawei.cms.dao.ArticleMapper;
import com.bawei.cms.domain.Article;
import com.bawei.cms.service.ArticleService;

/**
 * 说明:
 * 
 * @author howsun ->[howsun.zhang@gmail.com]
 * @version 1.0
 *
 * 2019年4月21日 下午9:06:07
 */
@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

	@Resource
	ArticleMapper articleMapper;

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<Article> gets(Article conditions, Page page, LinkedHashMap<String, Boolean> orders) {
		List<Article> articles = articleMapper.selects(conditions, orders, page);
		if(page != null && page.getPageCount() == 0){
			int totalCount = articleMapper.count(conditions);
			page.setTotalCount(totalCount);
		}
		return articles;
	}
	
	
	public List<Article> selects( Article article, LinkedHashMap<String, Boolean> orders,Page page){
		
		return articleMapper.selects(article, orders, page);
		
	}


	@Override
	public int count(Article article) {
		return articleMapper.count(article);
	}

	//根据id 查询文章
	@Override
	public Article selectByPrimaryKey(Integer id) {
		
		return articleMapper.selectByPrimaryKey(id);
	}

	//根据id 更新文章
	@Override
	public int updateByKey(Article article) {
		// TODO Auto-generated method stub
		return articleMapper.updateByKey(article);
	}


	@Override
	public void saveBlog(Article article) {
		articleMapper.save(article);;
	}


	@Override
	public void remove(Integer id) {
		articleMapper.remove(id);
	}


	@Override
	public List<Article> queryAll(Article article) {
		return articleMapper.queryAll(article);
	}


	@Override
	public void increaseHit(Integer id) {
		articleMapper.increaseHit(id);
	}


	@Override
	public Article queryWithTerm(Integer id) {
		// TODO Auto-generated method stub
		return articleMapper.queryWithTerm(id);
	}
	
	
	
}
