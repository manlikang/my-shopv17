package com.fuhan.v17userservice.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtUtils {

    /**
     *  密钥由调用方来决定
     */
    private String secretKey;
    /**
     * 有效期也由调用方来决定
     */
    private long ttl;

    public JwtUtils(String secretKey){
        this.secretKey=secretKey;
    }
    public String createJwtToken(String id,String subject){
        long now = System.currentTimeMillis();
        JwtBuilder jwtBuilder = Jwts.builder()
                .setId(id).setSubject(subject)
                .setIssuedAt(new Date(now))
                .signWith(SignatureAlgorithm.HS256,secretKey);
        if(ttl > 0){
            jwtBuilder.setExpiration(new Date(now+ttl));
        }
        return jwtBuilder.compact();
    }

    public Claims parseJwtToken(String jwtToken){
        return Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(jwtToken).getBody();
    }
}
