//package com.chat.util;
//
//import io.jsonwebtoken.*;
//import io.jsonwebtoken.security.Keys;
//
//import java.security.Key;
//import java.util.Date;
//
//public class JwtUtils {
//    //todo:更改时间
//    private static final long EXPIRATION_TIME = 1000 * 99 * 99 * 99; // 1天
//    private static final String SECRET_KEY = "SuperSecretKeyForJWT12345678901234567890"; // >= 32 位
//
//    private static final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
//
//    public static String generateToken(String username) {
//        return Jwts.builder()
//                .setSubject(username)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
//                .signWith(key, SignatureAlgorithm.HS256)
//                .compact();
//    }
//
//    public static String parseToken(String token) {
//        try {
//            return Jwts.parserBuilder()
//                    .setSigningKey(key)
//                    .build()
//                    .parseClaimsJws(token)
//                    .getBody()
//                    .getSubject();
//        } catch (JwtException e) {
//            return null; // token 无效或过期
//        }
//    }
//}
