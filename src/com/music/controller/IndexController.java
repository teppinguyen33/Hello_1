package com.music.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.music.entity.SongSearchParam;

@Controller
public class IndexController {

	@RequestMapping(value = "/music", method = RequestMethod.GET)
	public ModelAndView index() {
		return new ModelAndView("music/musicIndex", "songSearchParam", new SongSearchParam());
	}
	
}
