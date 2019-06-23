package com.bawei.test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bawei.cms.dao.TermMapper;
import com.bawei.cms.domain.Term;
import com.zhangsan.common.utils.StreamUtil;

public class TestCMS {

	//从文件读取标签数据写入到到数据库
	@Test
	public void insertTermIntoDB(){
		InputStream input = null;
		try {
			input = new FileInputStream("D:/八维/小实训/小一/资料/1701F/月考/标签.txt");
			String termText = StreamUtil.readTextFile(input);
			String[] terms = termText.split("\\|");
			//因为当前项目没有启动，所以需要手动读取Spring容器
			ApplicationContext content = new ClassPathXmlApplicationContext("spring-context.xml");
			TermMapper termMapper = content.getBean(TermMapper.class);
			for(String term : terms){
				Term t = new Term();
				t.setName(term);
				termMapper.addTerm(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			StreamUtil.closeAll(input);
		}
		
	}
	
	@Test
	public void getKeys(){
		//因为当前项目没有启动，所以需要手动读取Spring容器
		ApplicationContext content = new ClassPathXmlApplicationContext("spring-context.xml");
		TermMapper termMapper = content.getBean(TermMapper.class);
		Term t1 = new Term();
		Term t2 = new Term();
		Term t3 = new Term();
		t1.setName("a");
		t2.setName("b");
		t3.setName("c");
		termMapper.addTerm(t1);
		termMapper.addTerm(t2);
		termMapper.addTerm(t3);
		System.out.println(t1.getId());
		System.out.println(t2.getId());
		System.out.println(t3.getId());
	}
}
