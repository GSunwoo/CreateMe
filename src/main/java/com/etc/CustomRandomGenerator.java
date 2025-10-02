package com.etc;

import java.security.SecureRandom;
import java.util.Random;

public class CustomRandomGenerator {
	private static final String CHARACTERS = "ABCDEFGHJKLMNOPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz0123456789";
    private static final SecureRandom random = new SecureRandom();

    // 랜덤 비밀번호 생성
    public static String generatePass(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }
    
    // 랜덤 문자열 생성
 	public static String generateRandomString(String input, int length) {
         // 입력값의 해시코드를 시드로 사용
         long seed = input.hashCode();
         Random random = new Random(seed);

         StringBuilder sb = new StringBuilder();
         for (int i = 0; i < length; i++) {
             int idx = random.nextInt(CHARACTERS.length());
             sb.append(CHARACTERS.charAt(idx));
         }

         return sb.toString();
     }
}
