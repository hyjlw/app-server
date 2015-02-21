package org.mob.app.service.impl;

import java.util.List;

import org.mob.app.dao.BookMapper;
import org.mob.app.pojo.Book;
import org.mob.app.pojo.Criteria;
import org.mob.app.service.BookService;
import org.mob.app.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
	private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

	@Autowired
	private BookMapper bookMapper;
	
	@Override
	public int countByExample(Criteria example) {
		return bookMapper.countByExample(example);
	}

	@Override
	public List<Book> selectByExample(Criteria example) {
		return bookMapper.selectByExample(example);
	}

	@Override
	public String saveBook(Criteria example) {
		Book book = (Book) example.get("book");
		book.setCreateDate(DateUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
		
		logger.info("Save book: " + book);
		
		int result = 0;
		if(book.getId() == null) {
			result = bookMapper.insert(book);
		} else {
			result = bookMapper.updateByPrimaryKey(book);
		}
		
		return result > 0 ? "01" : "00";
	}

	@Override
	public String delete(Criteria example) {
		Integer id = example.getAsInteger("id");
		int result = bookMapper.deleteByPrimaryKey(id);
		
		return result > 0 ? "01" : "00";
	}

}
