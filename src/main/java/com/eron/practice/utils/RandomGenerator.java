package com.eron.practice.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

public class RandomGenerator {
	
	public static String generateString() {
		String method1 = new Random().ints(10, 33, 122).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
		String method2 = new Random().ints(10, 48, 122).mapToObj( i -> String.valueOf((char)i)).collect(Collectors.joining());
		
		return method1;
	}
	
	public static Integer generateIntger(Integer min, Integer max) {
		Integer randomInteger = (int) ((Math.random() * (max - min) ) + min);
		return randomInteger;
	}
	
	public static String generateRangeEnum() {
    	List<String> maleOrfemale = new ArrayList<String>() {
    		{
    			add("male");
    			add("female");
    		}
    	};
    	Random r = new Random();
    	
    	return maleOrfemale.get(r.nextInt(maleOrfemale.size()));
	}
	
	public static String generateUUID() {
		return UUID.randomUUID().toString();
	}

}



