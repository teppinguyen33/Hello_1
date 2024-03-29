package com.music.controller;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.common.MusicMP3Enum;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.music.common.CallAPIGetMethod;
import com.music.entity.Song;
import com.music.entity.SongData;
import com.music.entity.SongSearchParam;

@Controller
public class PlayController {

	@RequestMapping(value = "/music/songByCode/{songId}", method = RequestMethod.GET)
	public ModelAndView songByCode(@PathVariable("songId") String songId) {

		Song song = new Song();
		String url = MusicMP3Enum.HOST.getText() + MusicMP3Enum.SONG.getText()
				+ songId;

		try {
			CallAPIGetMethod callAPI = new CallAPIGetMethod(url);
			byte[] responseBody = callAPI.getResponseBody();

			ObjectMapper objectMapper = new ObjectMapper();
			SongData songData = objectMapper.readValue(responseBody,
					SongData.class);
			if ("1".equals(songData.getError())) {
				ModelAndView musicIndex = new ModelAndView("music/musicIndex");
				musicIndex.addObject("song", song);
				musicIndex.addObject("songSearchParam", new SongSearchParam());
				return musicIndex;
			}
			song.setTitle(songData.getData().getTitle());
			songData.getData().getQualities().sync();
			song.setQualities(songData.getData().getQualities());

		} catch (HttpException he) {
			he.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ModelAndView modelAndView = new ModelAndView("music/musicIndex");
		modelAndView.addObject("songSearchParam", new SongSearchParam());
		modelAndView.addObject("song", song);
		return modelAndView;
	}
}
