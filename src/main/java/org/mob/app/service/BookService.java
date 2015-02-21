package org.mob.app.service;

import java.util.List;

import org.mob.app.pojo.Book;
import org.mob.app.pojo.Criteria;

public interface BookService {
	int countByExample(Criteria example);

	List<Book> selectByExample(Criteria example);

	/**
	 * 
	 * @param example
	 * @return 00：失败，01：成功 ,其他情况
	 */
	String saveBook(Criteria example);

	/**
	 * 
	 * @param example
	 * @return 00：失败，01：成功 ,其他情况
	 */
	String delete(Criteria example);
	
}