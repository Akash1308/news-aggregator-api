package com.example.News.Aggregator.API.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {

        private String secretKey;

        public JWTService(){
            secretKey = generateSecretKey();
        }

        public String generateSecretKey(){
            try{
                KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
                SecretKey secretKey = keyGen.generateKey();
                System.out.println("Secret Key : " + secretKey.toString());
                return Base64.getEncoder().encodeToString(secretKey.getEncoded());
            } catch (NoSuchAlgorithmException e){
                throw new RuntimeException(" Error Generating secret key", e);
            }
        }

        public String generateToken(String email) {

            Map<String, Object> claims = new HashMap<>();

            // Add the subject (sub) as a claim in the map
            claims.put("sub", email);  // 'sub' is the claim for the subject (user)
            claims.put("iat", new Date(System.currentTimeMillis())); // Issued at claim
            claims.put("exp", new Date(System.currentTimeMillis() + 1000*60*30));

            return Jwts.builder()
                    .claims(claims)
                    .signWith(getKey(), SignatureAlgorithm.HS256)
                    .compact(); // Build the token
        }

        private Key getKey(){
            byte[] keyBytes = Decoders.BASE64.decode(secretKey);
            return Keys.hmacShaKeyFor(keyBytes);
        }

        public String extractUsername(String token) {
            // extract the username from the token
            return extractClaim(token, Claims::getSubject);
        }

        private <T> T extractClaim(String token, Function<Claims, T> claimResolver){
            final Claims claims = extractAllClaims(token);
            return claimResolver.apply(claims);
        }

        private Claims extractAllClaims(String token){
            return Jwts.parser()
                    .setSigningKey(getKey())
                    .build().parseClaimsJws(token).getBody();
        }

        public boolean validateToken(String token, UserDetails userDetails) {
            final String userName = extractUsername(token);
            return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
        }

        private boolean isTokenExpired(String token){
            return extractExpiration(token).before(new Date());
        }

        private Date extractExpiration(String token){
            return extractClaim(token, Claims::getExpiration);
        }


}
