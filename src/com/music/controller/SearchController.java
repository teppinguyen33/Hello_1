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
	
	private static final String KEYWORD_START = "<h2 class=\"title-main-item\"><span>";
	private static final String KEYWORD_END = "</span></h2>";
	private static final String RESULT_START = "<h3 class=\"title-sub\">";
	private static final String RESULT_END = "</h3>";
	private static final String ID = ".html";
	private static final String NAME_START = "<h2 class=\"title-song ellipsis-2\">";
	private static final String NAME_END = "</h2>";
	private static final String ARTIST_START = "<h3 class=\"title-sub\">";
	private static final String ARTIST_END = "</h3>";
	private static final String LISTEN_START = "<span class=\"nu\">";
	private static final String LISTEN_END = "</span>";
	
	@RequestMapping(value = "/music/search", method = RequestMethod.GET)
	public ModelAndView searchSongName(
			@ModelAttribute SongSearchParam songSearchParam) {

		List<Song> listSong = null;
		String keyword = null;
		String numberOfResult = null;
		
		try {
			String param = "?q="
					+ URLEncoder.encode(songSearchParam.getQ(), "UTF-8");

			String url = MusicMP3Enum.HOST.getText()
					+ MusicMP3Enum.SEARCH.getText() + param;

			CallAPI callAPI = new CallAPI(url);
			byte[] responseBody = callAPI.getResponseBody();

			String responseString = Utilities.decompress(responseBody);

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
			for (int i = 0; i < numberOfSong; i++) {
				
				// Lấy số kết quả trả về và keyword search
				if (i == 0) {
					start = rawSong[i].indexOf(KEYWORD_START) + KEYWORD_START.length();
					end = rawSong[i].indexOf(KEYWORD_END);
					keyword = rawSong[i].substring(start, end);
					
					start = rawSong[i].indexOf(RESULT_START) + RESULT_START.length();
					end = rawSong[i].indexOf(RESULT_END);
					numberOfResult = rawSong[i].substring(start, end);
					continue;
				}
				
				// Lấy id của bài hát
				start = rawSong[i].indexOf(ID) - 8;
				end = start + 8;
				if (start == -1) {
					break;
				} else {
					id = rawSong[i].substring(start, end);
				}

				// Lấy tên bài hát
				start = rawSong[i]
						.indexOf(NAME_START) + NAME_START.length();
				end = rawSong[i].indexOf(NAME_END);
				name = rawSong[i].substring(start, end);

				// Lấy tên nghệ sĩ
				start = rawSong[i].indexOf(ARTIST_START) + ARTIST_START.length();
				end = rawSong[i].indexOf(ARTIST_END);
				artist = rawSong[i].substring(start, end);

				// Lấy số lượt nghe
				start = rawSong[i].indexOf(LISTEN_START) + LISTEN_START.length();
				end = rawSong[i].indexOf(LISTEN_END);
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
		modelAndView.addObject("keyword", keyword);
		modelAndView.addObject("numberOfResult", numberOfResult);

		return modelAndView;
	}
}
