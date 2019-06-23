package com.bawei.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bawei.cms.domain.Term;

public interface TermMapper {

	void addTerm(Term term);

	void addBatch(List<Term> terms);

	void saveWithTerm(@Param("article_id")int article_id, 
			@Param("term_id")int term_id);
}
