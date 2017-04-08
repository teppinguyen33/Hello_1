package com.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
	
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public ModelAndView hello(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String loginId = (String) session.getAttribute("login");
		if (loginId == null) {
			return new ModelAndView("login");
		}
		return new ModelAndView("hello");
	}
}
