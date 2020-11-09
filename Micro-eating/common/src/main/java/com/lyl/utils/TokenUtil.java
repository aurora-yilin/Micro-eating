package com.lyl.utils;

import io.jsonwebtoken.*;

import java.util.*;

/**
 * Token工具类
 * @author lyl
 * @Date 2020/9/26 10:06
 */
public class TokenUtil {
    /**
     * 签名密钥
     */
    private String secret;
    /**
     * 有效期多少秒
     */
    private Long expiration;

    private String header;

    public TokenUtil(Builder builder) {
        this.secret = builder.secret;
        this.expiration = builder.expiration;
        this.header = builder.header;
    }

    /**
     * 添加用户名和权限到token中
     * @param userName
     * @param permissions
     * @return
     */
    public String generateToken(String userName, Collection permissions){
        Map<String,Object> claims = new HashMap<>(3);
        claims.put("sub",userName); //sub
        claims.put("created",new Date(System.currentTimeMillis())); //created
        claims.put("permissions", permissions);
        return generateToken(claims);
    }

    public String generateToken(String userName, Collection permissions,Long anonymityExpiration){
        Map<String,Object> claims = new HashMap<>(3);
        claims.put("sub",userName); //sub
        claims.put("created",new Date(System.currentTimeMillis())); //created
        claims.put("permissions", permissions);
        Date expirationDate = new Date(System.currentTimeMillis()+anonymityExpiration);
        return Jwts.builder().setClaims(claims)
                .setExpiration(expirationDate) //exp
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }

    /**
     * 设置过期时间，设置载荷，设置签名算法，设置签名密钥
     * @param claims
     * @return
     */
    private String generateToken(Map<String,Object> claims){
        Date expirationDate = new Date(System.currentTimeMillis()+expiration);
        return Jwts.builder().setClaims(claims)
                .setExpiration(expirationDate) //exp
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }

    /**
     * 刷新令牌，重新设置令牌的创建日期和过期时间
     * @param token
     * @return
     */
    public String refreshToken(String token) throws ExpiredJwtException,
            UnsupportedJwtException,
            MalformedJwtException,
            SignatureException,
            IllegalArgumentException{
        String refreshedToken;
        try{
            Claims claims = getClaimsFromToken(token);
            claims.put("created",new Date());
            refreshedToken = generateToken(claims);
        } catch (ExpiredJwtException e) {
            throw e;
        } catch (UnsupportedJwtException e) {
            throw e;
        } catch (MalformedJwtException e) {
            throw e;
        } catch (SignatureException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw e;
        }
        return refreshedToken;
    }

    /**
     * 根据token令牌中的过期时间判断令牌是否过期
     * 返回true 为过期，false为不过期
     * @param token
     * @return
     */
    public Boolean isTokenExpired(String token) throws
            ExpiredJwtException,
            UnsupportedJwtException,
            MalformedJwtException,
            SignatureException,
            IllegalArgumentException{
        try{
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (ExpiredJwtException e) {
            throw e;
        } catch (UnsupportedJwtException e) {
            throw e;
        } catch (MalformedJwtException e) {
            throw e;
        } catch (SignatureException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    /**
     * 获取到令牌中的userName
     * @param token
     * @return
     */
    public String getUserNameFromToken(String token) throws
            ExpiredJwtException,
            UnsupportedJwtException,
            MalformedJwtException,
            SignatureException,
            IllegalArgumentException{
        String userName;
        try{
            Claims claims = getClaimsFromToken(token);
            userName = claims.getSubject();
        } catch (ExpiredJwtException e) {
            throw e;
        } catch (UnsupportedJwtException e) {
            throw e;
        } catch (MalformedJwtException e) {
            throw e;
        } catch (SignatureException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw e;
        }
        return userName;
    }

    /**
     * 获取token令牌中的权限信息
     * @param token
     * @return
     */
    public Collection getPermissionsFromToken(String token) throws
            ExpiredJwtException,
            UnsupportedJwtException,
            MalformedJwtException,
            SignatureException,
            IllegalArgumentException{
        Collection permissions;
        try{
            Claims claims = getClaimsFromToken(token);
            permissions = (Collection) claims.get("permissions",Collection.class);
        } catch (ExpiredJwtException e) {
            throw e;
        } catch (UnsupportedJwtException e) {
            throw e;
        } catch (MalformedJwtException e) {
            throw e;
        } catch (SignatureException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw e;
        }
        return permissions;
    }

    /**
     * 从令牌中获取载荷数据
     * @param token
     * @return
     */
    private Claims getClaimsFromToken(String token)throws ExpiredJwtException,
            UnsupportedJwtException,
            MalformedJwtException,
            SignatureException,
            IllegalArgumentException{
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            throw e;
        } catch (UnsupportedJwtException e) {
            throw e;
        } catch (MalformedJwtException e) {
            throw e;
        } catch (SignatureException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw e;
        }
        return claims;
    }


    public static final class Builder{
        /**
         * 签名密钥
         */
        private String secret="asdfghjk";
        /**
         * 有效期多少秒
         */
        private Long expiration=360000L;

        /**
         * 携带token的请求头
         */
        private String header = "tokenAuth";

        public Builder(String secret, Long expiration, String header) {
            this.secret = secret;
            this.expiration = expiration;
            this.header = header;
        }

        public Builder() {
        }

        public Builder setSecret(String secret) {
            this.secret = secret;
            return this;
        }

        public Builder setExpiration(Long expiration) {
            this.expiration = expiration;
            return this;
        }

        public Builder setHeader(String header) {
            this.header = header;
            return this;
        }

        public TokenUtil build(){
            return new TokenUtil(this);
        }
    }
}
