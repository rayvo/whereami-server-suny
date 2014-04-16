package com.suny.whereami.service;

import java.util.List;

import com.suny.whereami.service.google.Place;

public class SimilarPlaces {
	
	private String keyword;
	private List<Place> similarPlaces;
	
	public SimilarPlaces(String _keyword, List<Place> places) {
		keyword = _keyword;
		similarPlaces = places;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public List<Place> getSimilarPlaces() {
		return similarPlaces;
	}
	public void setSimilarPlaces(List<Place> similarPlaces) {
		this.similarPlaces = similarPlaces;
	}
	
	
}
