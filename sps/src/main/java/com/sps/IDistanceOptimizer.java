package com.sps;


public interface IDistanceOptimizer {

	   void addConnection(String a, String b, Double distance, boolean bidirection);

	 

	   Double computeShortestDistance(String a, String b); // TODO: Return -9999 if no route found

	}
