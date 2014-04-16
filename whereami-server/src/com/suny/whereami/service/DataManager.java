package com.suny.whereami.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

import com.suny.whereami.service.google.Place;
import com.suny.whereami.service.google.Places;
import com.suny.whereami.service.google.Places.Params;
import com.suny.whereami.service.google.Places.Params.RankBy;
import com.suny.whereami.service.google.Places.Response;

public class DataManager {

	// private int radius = 80; //work, 67 > radius || radius > 94 : don't work
	//private DataManager man;
	private List<SimilarPlaces> map;
	private int numKeywords;
	private List<Place> result;
	
	public DataManager() {
		//man = new DataManager();
	}
		// TODO Auto-generated constructor stub
	

	public static int radius = 50;		//the average
	public static int radiusIncIndex = 10; //in the densed areas 10
	public static int MAX_RADIUS = 300; // take a photo from a far 300
	public static int MIN_RADIUS = 10; //same building, different floors 10

	
	
	public static void main(String[] args) {

		DataManager manager = new DataManager();
		List<String> keywords = new ArrayList<String>();

//2 keywords ///////////////////////////////////////////////////////////////////////////////////////
		/*
		 * Test 1: OK
		 * Result: 37.564785,126.995688
		 * Run time:25209???
		 * So slow
		 */		
		  /*double lat = 37.517422; double lon = 127.046848;
		  keywords.add("Paris Baguette".toLowerCase());
		  keywords.add("FamilyMart".toLowerCase());*/
		  
		/*	Test 2: OK
		 *  40.771287,-73.966038
		 *  Run time:5075
		 *  Incorrect Cell-tower location --> incorrect result */ 
		/* double lat = 40.69017; double lon = -73.99473;
		  keywords.add("ralph lauren".toLowerCase());
		  keywords.add("sigerson".toLowerCase());*/
		 
		
		/*	Test 3: OK
		 * 	37.592567,126.6745115
		 * 	Run time:6728		 		  
		 */
		/* double lat = 37.592075; double lon = 126.683438;
		  keywords.add("커피베이".toLowerCase());
		  keywords.add("신토오리".toLowerCase());*/
		
		/*
		 * 	Test 4: OK
		 * 	37.377611,126.667802
		 * 	Run time:4641
		 */
		 double lat = 37.38974; double lon = 126.667192;
		 /*keywords.add("한국뉴욕주립대");		 
		 keywords.add("포스코");*/
		 keywords.add("SUNY Korea");
		 //keywords.add("Songdo Global Campus");
		 keywords.add("Yonsei Institue of Convergence Technology");
		
		/*
		 * Test 5: OK
		 * 38.9095335,-77.0461555
		 * Run time:3028
		 */
		/*double lat = 38.909645; double lon = -77.043444;
		  keywords.add("sakana".toLowerCase());
		  keywords.add("lustre cleaners".toLowerCase());*/
		
		/*
		 * Test 6: OK
		 * 40.743642,-73.989159
		 * Run time: 53706,52075,55214
		 */
		/*double lat = 40.742665; double lon =-73.988581;		
		keywords.add("Memories of New York".toLowerCase()); //
		keywords.add("jmn fashian".toLowerCase()); //40.743642,-73.989159
		keywords.add("Flat Jron Hotel".toLowerCase()); //40.743192,-73.988566
*/		
		

//3 keywords ///////////////////////////////////////////////////////////////////////////////////////
		
		/*	Test 1: OK
		 *  40.686271,-73.99096566666667
		 *  Run time: 13410,16527, 14815, 14847
		 */			
			/*double lat = 40.672; double lon = -73.97717;
			keywords.add("starbucks"); 
			keywords.add("meat market"); 
			keywords.add("Sessame");*/

		/*
		 * Test 2: OK
		 * Result: 38.91076,-77.03833033333332
		 * Run time:2819,2260, 2257	, 3358
		 */
		/*double lat = 38.909645; double lon = -77.043444;
		  keywords.add("agora".toLowerCase());
		  keywords.add("trio's fox".toLowerCase());
		  keywords.add("gallery 2000".toLowerCase());*/
		  
		/*
		 * Test 3: OK
		 * Result: 40.74321975,-73.989342
		 * Run time:5109
		 */
		/*double lat = 40.742665; double lon =-73.988581;		
		keywords.add("Noir et blanc".toLowerCase()); //40.743192,-73.988566
		keywords.add("Hill Country Chicken".toLowerCase()); //
		keywords.add("NYKB".toLowerCase()); //
		keywords.add("Memories of New York".toLowerCase()); //40.743642,-73.989159		
		keywords.add("JMN Fashion".toLowerCase()); //40.743192,-73.988566
*/		

	
		
		/*****************************************************************************************/
		
		
		//keywords.add("그린컴퓨터학원".toLowerCase());
		/*
		 * double lat = 37.500613; double lon = 127.029006;
		 * keywords.add("UNI QLO".toLowerCase());
		 * keywords.add("그린컴퓨터학원".toLowerCase());
		 */

		
		 
	 
	
		/*double lat =  37.564130;
		double lon = 126.986274;

		
		keywords.add("불고기 브라더스");
		keywords.add("외환은행");*/

		/*double lat =  40.671820; 
		double lon = -73.977611;
		
		lat =    40.718823;
		lon = -74.003525;
		keywords.add("Starbucks".toLowerCase());
		keywords.add("Clay Pot".toLowerCase());*/
		
		
		/*double lat = 37.534340; double lon = 126.994672; 
		keywords.add("helios"); 
		keywords.add("SAERA");
		*/
		
		  List<String> kw = new ArrayList<String>();
			for (int i = 0; i < keywords.size(); i++) {
				//System.out.println(keywords.get(i));
				String keyword = "*" + keywords.get(i).trim().toLowerCase() + "*";
				kw.add(keyword);
			}
			
		Place result;
		try {
			result = manager.getLocation(kw, lat, lon);
			if (result != null) {
			System.out.println("\n" + result.getLatitude() + ","
					+ result.getLongitude());
		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	/**
	 * @param args
	 * @throws IOException
	 */

	public Place getLocation(List<String> keywords, double _lat, double _lon)
			throws IOException {
		System.out.println("Start matching...");
		long startTime = System.currentTimeMillis();		
		
		numKeywords = keywords.size();
		//List<Place> result = getPlaces(keywords, _lat, _lon);
		List<Place> result = getPlacesTest(keywords, _lat, _lon);
		Place location = null;
		if (result != null && result.size() > 0) {
			location = result.get(0); // the first location

			if (numKeywords == 1) {
				System.out.println("\nCount:" + 1);
				long endTime   = System.currentTimeMillis();
				long totalTime = endTime - startTime;
				System.out.println("Run time:" + totalTime);
				System.out.println("End.");
				return location;
			}

			double lat, lon;
			double tmpLat = 0, tmpLon = 0;

			int count = 0;
			for (Place place : result) {
				lat = place.getLatitude();
				lon = place.getLongitude();

				tmpLat = tmpLat + lat;
				tmpLon = tmpLon + lon;

				count++;
			}

			lat = tmpLat / count;
			lon = tmpLon / count;

			location = new Place();
			location.setLatitude(lat);
			location.setLongitude(lon);

			System.out.println("\nCount:" + count);
			System.out.println("\nResult: " + lat + ","
					+ lon);
		} else {
			System.out.println("Could not find any place!");

		}
		
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("Run time:" + totalTime);
		System.out.println("End.");
		return location;

	}

	private List<Place> getPlaces(List<String> keywords, double lat, double lon)
			throws IOException {

		if (numKeywords == 0)
			return null;

		
		result = new ArrayList<Place>();
		
		int R = 6371; // km
		if (numKeywords == 1) {
			//System.out.println("First KeyWord: " + keywords.get(0));
			List<Place> places = getBusinessPlace(lat, lon, keywords.get(0), false);
			if (places != null ) {
				result.add(places.get(0)); //TODO later
				for (Place p:places){
					//TODO remove places out of the range of 20Km
					/*double d = Math.acos(Math.sin(lat)*Math.sin(p.getLatitude()) + 
					                  Math.cos(lat)*Math.cos(p.getLatitude()) *
					                  Math.cos(lat-p.getLatitude())) * R;
					
					System.out.println("Dis1:" + d);
					double dLat = lat-p.getLatitude();
					double dLon = lon-p.getLongitude();
					
					double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
					        Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(p.getLatitude()) * Math.cos(lat); 
					double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
					
					d = R * c;
					System.out.print("Dis2:" + d);
					if (d < 50) {
						result.add(p);
						System.out.print(": [OK] \t ");
					}  					*/
					/*System.out.print(" \t " + p.getName() + ": ");
					System.out.println(p.getLatitude() + ", "
							+ p.getLongitude());*/
					
				}
			}
			return result;
		}

		List<Place> places = null;
		
		map = new ArrayList<SimilarPlaces>();
		
		for (int k = 0; k < numKeywords; k++) {
			
			String keyword = keywords.get(k);
			
			//System.out.println("\nSearch: " + keyword);
			
			places = getBusinessPlace(lat, lon, keyword, false);
			
			if (places != null && places.size() > 0) {
				
				//System.out.println(keyword + "\t" + "Number of places found:" + places.size());
				SimilarPlaces simPlaces = new SimilarPlaces(keyword, places);
				
				
				map.add(simPlaces);
				for (Place place : places) {
					//System.out.print(place.getName() + ": ");
					//System.out.println(place.getLatitude() + ", "	+ place.getLongitude());
				}
			} else {
				System.out.println("Could  not find any store with a name = "
						+ keyword);
				return null;
			}
		}
				
		/*System.out
				.println("DONE 1ST STEP--------------------------------------------------------------------------\n");*/

		Collections.sort(map, new Comparator<SimilarPlaces>() {

			@Override
			public int compare(SimilarPlaces sp1, SimilarPlaces sp2) {
				int i = 0;
				if (sp1.getSimilarPlaces().size() > sp2.getSimilarPlaces().size()) {
					i = 1;
				}
				return i;
			}
		});		

		
		for (SimilarPlaces simPlaces : map) {

			places = simPlaces.getSimilarPlaces();
			//System.out.println("\nSearch with Keyword:" + simPlaces.getKeyword());
			boolean located = searchNeighbors(places, simPlaces.getKeyword());
			if (located) {
				break;
			}
		}

		return result;		
	}
	
	private List<Place> getPlacesTest(List<String> keywords, double lat, double lon)
			throws IOException {

		if (numKeywords == 0)
			return null;

		
		result = new ArrayList<Place>();
		
		int R = 6371; // km
		if (numKeywords == 1) {
			System.out.println("First KeyWord: " + keywords.get(0));
			List<Place> places = getBusinessPlace(lat, lon, keywords.get(0), false);
			if (places != null ) {
				result.add(places.get(0)); //TODO later
				for (Place p:places){
					//TODO remove places out of the range of 20Km
					/*double d = Math.acos(Math.sin(lat)*Math.sin(p.getLatitude()) + 
					                  Math.cos(lat)*Math.cos(p.getLatitude()) *
					                  Math.cos(lat-p.getLatitude())) * R;
					
					System.out.println("Dis1:" + d);
					double dLat = lat-p.getLatitude();
					double dLon = lon-p.getLongitude();
					
					double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
					        Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(p.getLatitude()) * Math.cos(lat); 
					double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
					
					d = R * c;
					System.out.print("Dis2:" + d);
					if (d < 50) {
						result.add(p);
						System.out.print(": [OK] \t ");
					}  					*/
					/*System.out.print(" \t " + p.getName() + ": ");
					System.out.println(p.getLatitude() + ", "
							+ p.getLongitude());*/
					
				}
			}
			return result;
		}

		List<Place> places = null;
		
		map = new ArrayList<SimilarPlaces>();
		
		for (int k = 0; k < numKeywords; k++) {
			
			String keyword = keywords.get(k);
			
			System.out.println("\nSearch: " + keyword);
			
			places = getBusinessPlace(lat, lon, keyword, false);
			
			if (places != null && places.size() > 0) {
				
				System.out.println(keyword + "\t" + "Number of places found:" + places.size());
				SimilarPlaces simPlaces = new SimilarPlaces(keyword, places);
				
				
				map.add(simPlaces);
				for (Place place : places) {
					System.out.print(place.getName() + ": ");
					System.out.println(place.getLatitude() + ", "	+ place.getLongitude());
				}
			} else {
				System.out.println("Could  not find any store with a name = "
						+ keyword);
				return null;
			}
		}
				
		System.out
				.println("DONE 1ST STEP--------------------------------------------------------------------------\n");

		Collections.sort(map, new Comparator<SimilarPlaces>() {

			@Override
			public int compare(SimilarPlaces sp1, SimilarPlaces sp2) {
				int i = 0;
				if (sp1.getSimilarPlaces().size() > sp2.getSimilarPlaces().size()) {
					i = 1;
				}
				return i;
			}
		});		

		
		for (SimilarPlaces simPlaces : map) {

			places = simPlaces.getSimilarPlaces();
			System.out.println("\nSearch with Keyword:" + simPlaces.getKeyword());
			boolean located = searchNeighborsTest(places, simPlaces.getKeyword());
			if (located) {
				break;
			}
		}

		return result;		
	}

	private boolean searchNeighbors(List<Place> places, String keyword) throws IOException {
		
		List<Place> curResult = null;
		if (places != null && places.size() > 0) {			
			for (Place place : places) {				
				//System.out.println("\n\nSearch with place name: " + place.getName());
				curResult = new ArrayList<Place>();
				curResult.add(place);
													
				int curRadius = radius;				
				boolean isThreshold = true;
				boolean isReducing = true;
				boolean isCheckReduction = false;
				
				while (isThreshold) {
					if (curRadius > DataManager.MAX_RADIUS
							|| curRadius < DataManager.MIN_RADIUS) {
						break;
					} else {
						//System.out.print("\nSearch neighbors with radius = " + curRadius + "------------------------------------------------\n");
						List<Place> neighbors = getNeighborPlaces( place.getLatitude(), place.getLongitude(), curRadius);
						
						if (neighbors != null && neighbors.size() > 0) {								
							if (!isCheckReduction) {
								if (neighbors.size() == 20) { isReducing = true; }									
								else { isReducing = false;}
								isCheckReduction = true;
							}
							
							if (isReducing) {
								if (neighbors.size() == 20) { curRadius = curRadius - radiusIncIndex;}									
								else { isThreshold = false; }
							} else { 
								if (neighbors.size() < 20) { curRadius = curRadius + radiusIncIndex; }									
								else { isThreshold = false;}
							}									
							//System.out.println("NUMBER OF NEIGHBORS: " + neighbors.size() + ": ");
							//for (Place neighbor : neighbors) {	System.out.print(neighbor.getName() + ", "); }								
							
							for (SimilarPlaces inSimPlaces : map) {
								
								if (!keyword.equals(inSimPlaces.getKeyword())) {
									List<Place> inPlaces = inSimPlaces
											.getSimilarPlaces();
									Place intersec = getIntersec(neighbors,
											inPlaces);
									if (intersec != null) {
										curResult.add(intersec);
									}
								}
							}

							if (curResult.size() == numKeywords) { result = curResult; return true;} 
							if (curResult.size() > result.size()) { result = curResult; }							
						} else {
							break; //There is no neighbor around this place
						}
					} // Reach to the search range					
				} //End While
			}
		}
		return false;
	}
	
private boolean searchNeighborsTest(List<Place> places, String keyword) throws IOException {
		
		List<Place> curResult = null;
		if (places != null && places.size() > 0) {			
			for (Place place : places) {				
				System.out.println("\n\nSearch with place name: " + place.getName());
				curResult = new ArrayList<Place>();
				curResult.add(place);
													
				int curRadius = radius;				
				boolean isThreshold = true;
				boolean isReducing = true;
				boolean isCheckReduction = false;
				
				while (isThreshold) {
					if (curRadius > DataManager.MAX_RADIUS
							|| curRadius < DataManager.MIN_RADIUS) {
						break;
					} else {
						System.out.print("\nSearch neighbors with radius = " + curRadius + "------------------------------------------------\n");
						List<Place> neighbors = getNeighborPlaces( place.getLatitude(), place.getLongitude(), curRadius);
						
						if (neighbors != null && neighbors.size() > 0) {								
							if (!isCheckReduction) {
								if (neighbors.size() == 20) { isReducing = true; }									
								else { isReducing = false;}
								isCheckReduction = true;
							}
							
							if (isReducing) {
								if (neighbors.size() == 20) { curRadius = curRadius - radiusIncIndex;}									
								else { isThreshold = false; }
							} else { 
								if (neighbors.size() < 20) { curRadius = curRadius + radiusIncIndex; }									
								else { isThreshold = false;}
							}									
							System.out.println("NUMBER OF NEIGHBORS: " + neighbors.size() + ": ");
							for (Place neighbor : neighbors) {	System.out.print(neighbor.getName() + ", "); }								
							
							for (SimilarPlaces inSimPlaces : map) {
								
								if (!keyword.equals(inSimPlaces.getKeyword())) {
									List<Place> inPlaces = inSimPlaces
											.getSimilarPlaces();
									Place intersec = getIntersec(neighbors,
											inPlaces);
									if (intersec != null) {
										curResult.add(intersec);
									}
								}
							}

							if (curResult.size() == numKeywords) { result = curResult; return true;} 
							if (curResult.size() > result.size()) { result = curResult; }							
						} else {
							break; //There is no neighbor around this place
						}
					} // Reach to the search range					
				} //End While
			}
		}
		return false;
	}
	
	private Place getIntersec(List<Place> neighbors, List<Place> inPlaces) {
		for (Place keyPlace : inPlaces) {
			String keyName = keyPlace.getName().toLowerCase();
			for (Place neighbor : neighbors) {
				String neighborName = neighbor.getName().toLowerCase();
				if (neighborName.equals(keyName)) {						
						return neighbor;						
				}
			}			
		}
		return null;
	}

	private List<Place> getPlaces1(List<String> keywords, double lat, double lon)
			throws IOException {

		if (keywords.size() == 0)
			return null;

		DataManager man = new DataManager();
		List<Place> result = new ArrayList<Place>();
		int R = 6371; // km
		if (keywords.size() == 1) {
			System.out.println("First KeyWord: " + keywords.get(0));
			List<Place> places = man.getBusinessPlace(lat, lon, keywords.get(0), false);
			if (places != null ) {
				result.add(places.get(0)); //TODO later
				for (Place p:places){
					//TODO remove places out of the range of 20Km
					double d = Math.acos(Math.sin(lat)*Math.sin(p.getLatitude()) + 
					                  Math.cos(lat)*Math.cos(p.getLatitude()) *
					                  Math.cos(lat-p.getLatitude())) * R;
					
					System.out.println("Dis1:" + d);
					double dLat = lat-p.getLatitude();
					double dLon = lon-p.getLongitude();
					
					double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
					        Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(p.getLatitude()) * Math.cos(lat); 
					double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
					d = R * c;
					System.out.print("Dis2:" + d);
					if (d < 50) {
						result.add(p);
						System.out.print(": [OK] \t ");
					}  					
					System.out.print(" \t " + p.getName() + ": ");
					System.out.println(p.getLatitude() + ", "
							+ p.getLongitude());
					
				}
			}
			return result;
		}

		int point = 0;

		Hashtable<String, List<Place>> keyPlacesHash = new Hashtable<String, List<Place>>();
		List<Place> places = null;

		
		for (int k = 0; k < keywords.size(); k++) {
			String keyword = keywords.get(k);
			System.out.println("\nSearch: " + keyword);
			
			places = man.getBusinessPlace(lat, lon, keyword, false);

			if (places != null && places.size() > 0) {
				for (Place place : places) {
					System.out.print(place.getName() + ": ");
					System.out.println(place.getLatitude() + ", "
							+ place.getLongitude());
				}
				keyPlacesHash.put(keyword, places);
				// System.out.println();
			} else {
				System.out.println("Could  not find any store with a name = "
						+ keyword);
				return null;
			}
		}
		
		Hashtable<String, List<Place>> sortedKeyAndPlaces = sortHash(keyPlacesHash);

		System.out
				.println("DONE 1ST STEP--------------------------------------------------------------------------\n");
		// Only need to use the first keyword
		places = keyPlacesHash.get(keywords.get(0));
		if (places != null && places.size() > 0) {

			for (Place place : places) {
				result.clear();

				System.out.print("\n" + place.getName() + ": ");

				result.add(place);
				point = 1;
				int curRadius = radius;
				boolean flag = true;
				boolean isFull = true;
				boolean isReducing = true;
				boolean isCheckReduction = false;
				while (isFull) {
					if (curRadius > DataManager.MAX_RADIUS
							|| curRadius < DataManager.MIN_RADIUS) {
						break;
					} else {
						System.out
								.print("\n Search neighbors with radius = "
										+ curRadius
										+ "------------------------------------------------\n");
						List<Place> neighbors = man.getNeighborPlaces(
								place.getLatitude(), place.getLongitude(),
								curRadius);

						if (neighbors != null && neighbors.size() > 0) {
							
							if (!isCheckReduction) {
								if (neighbors.size() == 20) {
									isReducing = true;									
								} else {
									isReducing = false;
								}
								isCheckReduction = true;
							}
							
							if (isReducing) {
								if (neighbors.size() == 20) {
									curRadius = curRadius - radiusIncIndex;									
								} else {
									//Break
									isFull = false;										
								}
							} else { 
								if (neighbors.size() < 20) {
									curRadius = curRadius + radiusIncIndex;									
								} else {
									//Break
									isFull = false;	
								}
							}
								

							System.out.print("NUMBER OF NEIGHBORS: " + neighbors.size() + ": ");
							for (Place neighbor : neighbors) {
								String neighborName = neighbor.getName();
								System.out.print(neighborName + ", ");
							}

							for (int i = 1; i < keywords.size(); i++) {
								List<Place> keyPlaces = keyPlacesHash
										.get(keywords.get(i)); // Start with the
																// second
																// keyword
								for (Place keyPlace : keyPlaces) {
									String keyName = keyPlace.getName()
											.toLowerCase();
									// System.out.println("\nRayVo:" +
									// keywords.get(i) +
									// "=" + keyName);
									flag = false;
									for (Place neighbor : neighbors) {
										String neighborName = neighbor
												.getName().toLowerCase();
										if (neighborName.equals(keyName)) {
											point++;
											result.add(neighbor);
											flag = true;
											break;
										}
									}
									if (flag) {
										break;
									}
								}
								if (!flag) { // Around of this place doesn't
												// contain the
												// keyword any item of keyPlaces
									break;
								}
							}
							System.out.println();

						} else {
							return null;
						}
					}

					System.out.println("POINT:" + point);
					if (point == keywords.size()) {
						return result; // Return only the first set
					}

				}
			}

		}
		return result;
	}

	private Hashtable<String, List<Place>> sortHash(
			Hashtable<String, List<Place>> keyPlacesHash) {
		Hashtable<String, List<Place>> result = new Hashtable<String, List<Place>>();
		Collection keyPlaces = keyPlacesHash.values();
		for(int i = 0; i<keyPlaces.size(); i++){
			
		}
		return null;
	}

	/**
	 * @param lat
	 * @param lon
	 * @param keyword
	 * @return list of keywords around the (lat,lon) in the increasing distance
	 * @throws IOException
	 */
	public List<Place> getBusinessPlaces(double lat, double lon, String keyword)
			throws IOException {

		Response<List<Place>> search = Places.search(new Params()
				.location(lat, lon).rankBy(RankBy.DISTANCE)
				// .pageToken(next_page_token) TODO: will be used when more than
				// 20 results needed
				.keyword(keyword));

		/*
		 * Response<List<Place>> search = Places.nearbySearch(new Params()
		 * .location(lat, lon).rankBy(RankBy.DISTANCE) //
		 * .pageToken(next_page_token) TODO: will be used when more than // 20
		 * results needed .name(keyword) );
		 */

		return search.getResult();

	}
	Hashtable<String, String> tokens ;
	public List<Place> getBusinessPlace(double lat, double lon, String keyword, boolean isNext)
			throws IOException {
		tokens = new Hashtable<String, String>();
		Response<List<Place>> search = null;
		String token = "";
		if (!isNext) {
			search = Places.textSearch(new Params().location(
					lat, lon)
			// .pageToken(next_page_token) TODO: will be used when more than
			// 20 results needed
					.radius(20000)
					//.keyword(keyword)				
					.query(keyword));
			
			token = search.getNextPageToken();
			//System.out.println(token);
		
			if (token !=null && token.length() > 0){
				tokens.put(keyword, token);
			}
		} else { //second
			token = tokens.get(keyword);
			search = Places.textSearch(new Params().location(
					lat, lon)
					.pageToken(token)
			// 20 results needed
					.radius(20000)
					.keyword(keyword)				
					.query(keyword));
			
			token = search.getNextPageToken();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (token !=null && token.length() > 0){
				tokens.remove(keyword);
				tokens.put(keyword, token);
			}
		}
		return search.getResult();
	}

	public List<Place> getNeighborPlaces(double lat, double lon, int radius)
			throws IOException {
		Response<List<Place>> search = Places.nearbySearch(new Params()
				.location(lat, lon).radius(radius));

		return search.getResult();
	}

}
