package com.suny.whereami.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jvnet.hk2.annotations.Service;

import com.suny.whereami.service.google.Place;

/**
 * @author rayvo
 */
@Service
public class SearchService {

	public static Place searchLocation(double lat, double lon, String param) throws IOException {
		
		Map<String, Integer> photos_pool = new TreeMap<String, Integer>();
		String[] pharses = param.split("&");	
		String keyword = "";
		List<String> keywords = new ArrayList<String>();
		for (int i = 0; i < pharses.length; i++) {
			System.out.println(pharses[i]);
			keyword = "*" + pharses[i].trim().toLowerCase() + "*";
			keywords.add(keyword);
		}
		DataManager dataMan = new DataManager();
		Place result = dataMan.getLocation(keywords, lat, lon);			
	
		return result;
	}


}
