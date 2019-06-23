/**
 * 
 */
package com.bawei.cms.web.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bawei.cms.core.Page;
import com.bawei.cms.domain.Article;
import com.bawei.cms.domain.Term;
import com.bawei.cms.domain.User;
import com.bawei.cms.service.ArticleService;
import com.bawei.cms.service.TermService;
import com.bawei.cms.service.UserService;
import com.bawei.cms.utils.FileUploadUtil;
import com.bawei.cms.web.Constant;

/**
 * 说明:
 * 
 * @author howsun ->[howsun.zhang@gmail.com]
 * @version 1.0
 *
 * 2018年1月10日 下午2:40:38
 */
@Controller
@RequestMapping("/my")
public class UserController {

	@Autowired
	ArticleService articleService;
	
	@Autowired
	private TermService termService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping({"/", "/index", "/home"})
	public String home(){
		return "user-space/home";
	}
	
	@RequestMapping({"/profile"})
	public String profile(){
		return "user-space/profile";
	}
	
	@RequestMapping("/blogs")
	public String blogs(Model model,HttpSession session,@RequestParam(value="pageIndex",defaultValue="1")Integer pageIndex) {
		Article article = new Article();
		
		//用户信息
		User author = (User) session.getAttribute(Constant.LOGIN_USER);
		article.setAuthor(author);
		
		int totalCount = articleService.count(article);
		
		//分页信息
		Page page =  new Page(pageIndex,3);
		page.setTotalCount(totalCount);
		page.setUrl("/my/blogs");
		//文章列表
		List<Article> articles = articleService.selects(article, null, page);
		model.addAttribute("blogs", articles);
		model.addAttribute("page", page.toString());
		return "user-space/blog_list";
	}
	
	/*@RequestMapping("/blogs")
	public String blogs(Model model,HttpSession session,@RequestParam(value="page",defaultValue="1")Integer page) {
		Article article = new Article();
		
		//用户信息
		User author = (User) session.getAttribute(Constant.LOGIN_USER);
		article.setAuthor(author);
		
		
		PageHelper.startPage(page,3);
		//文章列表
		List<Article> articles = articleService.queryAll(article);
		//分页信息
		PageInfo<Article> pageInfo = new PageInfo<>(articles,3);
		String pageList = PageHelpUtil.page("blogs", pageInfo, null);
		model.addAttribute("blogs", articles);
		model.addAttribute("pageList", pageList);
		return "user-space/blog_list";
	}*/
	
	
	@RequestMapping("/blog/edit")
	public String edit(Integer id,Model model) {
		
		Article article = articleService.selectByPrimaryKey(id);
		model.addAttribute("blog", article);
		return "user-space/blog_edit";
	}
	
	
	//更新和删除在一起
	@RequestMapping("/blog/save")
	public String save(Article article,
			String[] name,MultipartFile file,HttpServletRequest request) {
		//文件上传
		String picture = FileUploadUtil.upload(request, file);
		article.setPicture(picture);
		if(article.getId() != null){
			articleService.updateByKey(article);
		}else{
		//第一次没有点击
		article.setHits(0);
		//是否是热点新闻
		article.setHot(true);
		//审核是否通过
		article.setStatus(1);
		//是否被删除
		article.setDeleted(false);
		//创建时间
		article.setCreated(new Date());
		//作者
		User user = (User) request.getSession().getAttribute(Constant.LOGIN_USER);
		article.setAuthor(user);
		
		//保存标签
		articleService.saveBlog(article);
		//---B卷部分内容
		//保存文章后还增加积分
//		userService.addScore(user.getId());
		
		//获取刚插入文章的主键
		int article_id = article.getId();
		
		
		//保存标签
		List<Term> terms = new ArrayList<>();
		for(String term : name){
			Term t = new Term();
			t.setName(term);
			terms.add(t);
		}
		//批量插入标签
		termService.addBatch(terms);
		
		//获取刚插入标签表的term主键
		for(Term term : terms){
			termService.saveWithTerm(article_id,term.getId());
		}
		//插入标签
		}
		return "redirect:/my/blogs";
	}
	
	//删除发布的文章
	@RequestMapping("/blog/remove")
	public String remove(Integer id){
		articleService.remove(id);
		return "redirect:/my/blogs";
	}
	
	/*
	 * A卷部分内容
	 * */
	//外部博客
	@RequestMapping("personal")
	public String personal(Model model,HttpSession session){
		User user = (User) session.getAttribute(Constant.LOGIN_USER);
		user = userService.get(user.getId());
		model.addAttribute("user", user);
		return "user-space/personal";
	}
	
	/*//修改外部博客
	@RequestMapping("/blog/eidtOutBlog")
	public String eidtOutBlog(Model model,HttpSession session){
		User user = (User) session.getAttribute(Constant.LOGIN_USER);
		user = userService.get(user.getId());
		model.addAttribute("user", user);
		return "user-space/personal";
	}*/
	
	//删除外部博客
	@RequestMapping("/blog/delOutBlog")
	public String delOutBlog(Model model,HttpSession session){
		User user = (User) session.getAttribute(Constant.LOGIN_USER);
		userService.update(user.getId());
		model.addAttribute("user", user);
		return "user-space/personal";
	}
	
}
