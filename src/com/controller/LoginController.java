package com.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.dao.LoginDAO;
import com.entity.User;

@Controller
public class LoginController {

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login() {
		return new ModelAndView("login", "user", new User());
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(
			HttpServletRequest request
			, @ModelAttribute("user") User user) {

		LoginDAO loginDAO = new LoginDAO();
		String loginId = null;
		try {
			loginId = loginDAO.login(user.getUsername(), user.getPassword());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(loginId != null) {
			HttpSession session = request.getSession();
			session.setAttribute("login", loginId);
			return new ModelAndView("index");
		} else {
			return new ModelAndView("login");
		}
	}
}
