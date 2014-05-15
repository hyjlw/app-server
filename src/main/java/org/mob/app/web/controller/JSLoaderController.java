package org.mob.app.web.controller;

import java.util.Date;
import java.util.Locale;

import org.mob.app.common.springmvc.DateConvertEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * js脚本加载测试
 */
@Controller
public class JSLoaderController {

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateConvertEditor());
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	/**
	 * yepnope
	 */
	@RequestMapping(value = "/yepnope", method = RequestMethod.GET)
	public String yepnope(Locale locale, Model model) {
//		return "loader/yepnope";
		return "user/resetpwd";
	}

	/**
	 * lab
	 */
	@RequestMapping(value = "/lab", method = RequestMethod.GET)
	public String lab(Locale locale, Model model) {
		return "loader/lab";
	}

	/**
	 * header
	 */
	@RequestMapping(value = "/headers", method = RequestMethod.GET)
	public String header(Locale locale, Model model) {
		return "loader/header";
	}

	/**
	 * loginlab
	 */
	@RequestMapping(value = "/loginlab", method = RequestMethod.GET)
	public String loginlab() {
		return "loader/login.LAB";
	}

	/**
	 * loginyepnope
	 */
	@RequestMapping(value = "/loginyepnope", method = RequestMethod.GET)
	public String loginyepnope() {
		return "loader/login.yepnope";
	}
}
