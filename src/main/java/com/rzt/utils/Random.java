package com.rzt.utils;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 
 * @author Jaun
 *
 */
public class Random {

	public static int randInt( int min, int max )
	{
		return ThreadLocalRandom.current().nextInt(min, max);
	}
}
