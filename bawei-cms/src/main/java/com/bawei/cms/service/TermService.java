package com.bawei.cms.service;

import java.util.List;

import com.bawei.cms.domain.Term;

public interface TermService {

	void addBatch(List<Term> terms);

	void saveWithTerm(int article_id, int id);

}
