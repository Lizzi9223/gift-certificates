package com.epam.esm.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {
  private static final Logger logger = Logger.getLogger(JwtProvider.class);
  private String jwtSecret = "secret"; //TODO

  public String generateToken(String login) {
    Date date = Date.from(LocalDate.now().plusDays(15).atStartOfDay(ZoneId.systemDefault()).toInstant());
    return Jwts.builder()
        .setSubject(login)
        .setExpiration(date)
        .signWith(SignatureAlgorithm.HS512, jwtSecret)
        .compact();
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
      return true;
    } catch (ExpiredJwtException expEx) {
      logger.warn("Token expired");
    } catch (UnsupportedJwtException unsEx) {
      logger.warn("Unsupported jwt");
    } catch (MalformedJwtException mjEx) {
      logger.warn("Malformed jwt");
    } catch (SignatureException sEx) {
      logger.warn("Invalid signature");
    } catch (Exception e) {
      logger.warn("invalid token");
    }
    return false;
  }

  public String getLoginFromToken(String token) {
    Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    return claims.getSubject();
  }
}
