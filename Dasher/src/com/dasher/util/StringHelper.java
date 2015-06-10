package com.dasher.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.sourceforge.pinyin4j.PinyinHelper;

public class StringHelper {

	 public static String getPinYinHeadChar(String str) {  
		  
	        String convert = "";  
	        for (int j = 0; j < str.length(); j++) {  
	            char word = str.charAt(j);  
	            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);  
	            if (pinyinArray != null) {  
	                convert += pinyinArray[0].charAt(0);  
	            } else {  
	                convert += word;  
	            }  
	        }  
	        return convert.toLowerCase();  
	}  
	 
    public static String getCode()
    {
    	String[] beforeShuffle = new String[] {"0","1", "2", "3", "4", "5", "6", "7",  
                "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",  
                "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",  
                "W", "X", "Y", "Z" };  
        List list = Arrays.asList(beforeShuffle);  
        Collections.shuffle(list);  
        StringBuilder sb = new StringBuilder();  
        for (int i = 0; i < list.size(); i++) {  
            sb.append(list.get(i));  
        }  
        String afterShuffle = sb.toString();  
        String result = afterShuffle.substring(5, 9);  
        return result;
    }
	   
	  
}
