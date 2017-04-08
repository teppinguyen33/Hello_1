package com.music.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.common.MusicMP3Enum;
import com.music.common.CallAPI;
import com.music.common.Utilities;
import com.music.object.Song;
import com.music.object.SongSearchParam;

@Controller
public class SearchController {
	
	@RequestMapping(value = "/music/search", method = RequestMethod.GET)
	public ModelAndView searchSongName(
			@ModelAttribute SongSearchParam songSearchParam) {

		List<Song> listSong = null;
		System.out.println(songSearchParam.getSongName());

		try {
			String param = "?q="
					+ URLEncoder.encode(songSearchParam.getSongName(), "UTF-8");

			System.out.println(param);

			String url = MusicMP3Enum.HOST.getText()
					+ MusicMP3Enum.SEARCH.getText() + param;

			CallAPI callAPI = new CallAPI(url);
			byte[] responseBody = callAPI.getResponseBody();

			String responseString = Utilities.decompress(responseBody);

			System.out.println(responseString);

			String[] rawSong = responseString.split("<a href=\"/bai-hat/");
			int numberOfSong = rawSong.length;
			if (numberOfSong > 1) {
				listSong = new ArrayList<Song>();
			}
			String id = null;
			String name = null;
			String artist = null;
			String numberOfListen = null;
			int start = -1;
			int end = -1;
			for (int i = 1; i < numberOfSong; i++) {

				// Lấy id của bài hát
				start = rawSong[i].indexOf(".html") - 8;
				end = start + 8;
				if (start == -1) {
					break;
				} else {
					id = rawSong[i].substring(start, end);
				}

				// Lấy tên bài hát
				start = rawSong[i]
						.indexOf("<h2 class=\"title-song ellipsis-2\">") + 34;
				end = rawSong[i].indexOf("</h2>");
				name = rawSong[i].substring(start, end);

				// Lấy tên nghệ sĩ
				start = rawSong[i].indexOf("<h3 class=\"title-sub\">") + 22;
				end = rawSong[i].indexOf("</h3>");
				artist = rawSong[i].substring(start, end);

				// Lấy số lượt nghe
				start = rawSong[i].indexOf("<span class=\"nu\">") + 17;
				end = rawSong[i].indexOf("</span>");
				numberOfListen = rawSong[i].substring(start, end);

				Song song = new Song();
				song.setId(id);
				song.setTitle(name);
				song.setArtist(artist);
				song.setNumberOfListen(numberOfListen);
				listSong.add(song);
			}

		} catch (UnsupportedEncodingException ue) {
			ue.printStackTrace();
		} catch (HttpException he) {
			he.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ModelAndView modelAndView = new ModelAndView("music/musicResult");
		modelAndView.addObject("listSong", listSong);
		modelAndView.addObject("songSearchParam", new SongSearchParam());

		return modelAndView;
	}
}
