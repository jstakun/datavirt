package com.redhat.waw.jstakun;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MaskingUtils {

	private static final Logger logger = Logger.getLogger(MaskingUtils.class.getName());
	
	public static void main(String[] args) {
        System.out.printf("'%s'\n", getScrambled(args[0]));
    }

    public static String getScrambled(String s) {
        try {
        	String[] scram = s.split("");
        	List<String> letters = Arrays.asList(scram);
        	Collections.shuffle(letters);
        	StringBuilder sb = new StringBuilder(s.length());
        	for (String c : letters) {
        		sb.append(c);
        	}
        	return sb.toString();
        } catch (Exception e) {
        	logger.log(Level.SEVERE, MaskingUtils.class.getName() + ".getScrambled() failed:" , e);
        	return s;
        }       
    }
}
