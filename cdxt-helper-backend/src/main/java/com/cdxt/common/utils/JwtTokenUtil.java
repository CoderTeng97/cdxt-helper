package com.cdxt.common.utils;

import com.cdxt.common.exception.ResponseCommonException;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;


@Component
@Slf4j
//@ConfigurationProperties(prefix = "jwt.config")
public class JwtTokenUtil implements Serializable {
    /**
     * 过期时间
     */
    @Value("${jwt.config.expiration}")
    public long expiration;
    /**
     * JWT密码
     */
    @Value("${jwt.config.secret}")
    public String secret;
    /**
     * Token前缀
     */
    @Value("${jwt.config.prefix}")
    public String prefix;
    /**
     * 存放Token的Header Key
     */
    @Value("${jwt.config.head}")
    public String head;

    private long serialVersionUID = 7455531721470217295L;

    /**
     * 生成Token
     *
     * @param claims
     * @return
     */
    public String generateToken(Map<String, Object> claims) {
        Date createdDate = new Date();
        Date expirationDate = new Date(createdDate.getTime() + expiration);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 判断是否过期
     *
     * @param claims
     * @return
     */
    private boolean canRefreshToken(Claims claims) {
        Date expirationDate = claims.getExpiration();
        Date expectDate = new Date(expirationDate.getTime() + expiration);
        // 过期一天不到,refresh
        return expirationDate.before(expectDate);
    }

    /**
     * 刷新token
     *
     * @param token
     * @return
     */
    public String refreshToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        if (canRefreshToken(claims)) {
            throw new ResponseCommonException(HttpStatus.UNAUTHORIZED, "TOKEN 过期需要重新认证");
        } else {
            return generateToken(claims);
        }
    }


    /**
     * 获取Claims
     *
     * @param token
     * @return
     */
    public Claims getAllClaimsFromToken(String token) {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (SignatureException ex) {
            throw new ResponseCommonException(HttpStatus.UNAUTHORIZED, "Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            throw new ResponseCommonException(HttpStatus.UNAUTHORIZED, "Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new ResponseCommonException(HttpStatus.UNAUTHORIZED, "Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new ResponseCommonException(HttpStatus.UNAUTHORIZED, "Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new ResponseCommonException(HttpStatus.UNAUTHORIZED, "JWT claims string is empty.");
        }
    }

}