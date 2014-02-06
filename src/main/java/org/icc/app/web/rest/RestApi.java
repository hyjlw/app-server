package org.icc.app.web.rest;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.icc.app.pojo.News;
import org.icc.app.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(headers = "Accept=application/json", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
public class RestApi {
	@Autowired
	private NewsService newsService;

	@RequestMapping(value = "/news")
	@ResponseBody
	public List<News> getNews(@RequestBody News news,
			HttpServletRequest request) {
		
		return newsService.selectAllNews();
	}
	
	@RequestMapping(value = "/upload")
	@ResponseBody
	public String upload(HttpServletRequest request, HttpServletResponse response) {
		byte[] bytes;
		try {
			InputStream ins = request.getInputStream();
			
			bytes = new byte[ins.available()];
			ins.read(bytes);
			String s = new String(bytes);
			System.out.println(s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return "success";
	}
}
