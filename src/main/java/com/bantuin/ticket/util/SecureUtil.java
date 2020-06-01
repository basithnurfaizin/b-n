package com.bantuin.ticket.util;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class SecureUtil {
    public static String getMd5(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String myHash = DatatypeConverter
                .printHexBinary(digest).toUpperCase();
        return myHash;
    }
    private static String shuffle(String base, String salt) {
        int halfLength = salt.length()/2;
        return salt.substring(0,halfLength) + base + salt.substring(halfLength);
    }

    private static String infix(String base, String salt) {
        // panjang base = 96, salt = 32
        StringBuilder sb = new StringBuilder(base);
        for(int i=15; i>=0; i--) {
            String infix = salt.substring(2*i, 2*i+2);
            sb.insert(6*i, infix);
        }
        return sb.toString();
    }

    private static String extract(String salted) {
        // panjang base = 96, salt = 32
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<16; i++) {
            sb.append(salted, 8*i, 8*i+2);
        }
        return sb.toString();
    }



    public static String generateRandomToken(String input) {
        String candidates = input.replaceAll("\\s+", "");
        List<Character> characters = new ArrayList<Character>();
        for (char c : candidates.toCharArray()) {
            characters.add(c);
        }
        StringBuilder output = new StringBuilder(candidates.length());
        while (characters.size() != 0) {
            int randPicker = (int) (Math.random() * characters.size());
            output.append(characters.remove(randPicker));
        }
        return output.toString();
    }



    public static String randomString( int len ){
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom rnd = new SecureRandom();

        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
    }
}
