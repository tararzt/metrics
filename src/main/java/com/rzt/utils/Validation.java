package com.rzt.utils;

public final class Validation {
	
	public static Boolean validateCoordinates(Double[] points){
		Boolean isValid = false;
		if(points != null && points.length == 2)
			isValid = true;
		return isValid;
	}

}
