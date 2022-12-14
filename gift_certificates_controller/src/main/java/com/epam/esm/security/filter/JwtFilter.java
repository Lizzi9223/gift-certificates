package com.epam.esm.security.filter;

import com.epam.esm.consts.Attributes;
import com.epam.esm.consts.URL;
import com.epam.esm.security.UserDetailsImpl;
import com.epam.esm.security.jwt.JwtProvider;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Filter to check JWT token
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
@Component
public class JwtFilter extends OncePerRequestFilter {
  private final JwtProvider jwtProvider;

  @Autowired
  public JwtFilter(JwtProvider jwtProvider) {
    this.jwtProvider = jwtProvider;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String token = getTokenFromRequest(request);
    String requestURL = request.getRequestURL().toString();
    try {
      if (StringUtils.hasText(token) && jwtProvider.validateToken(token)) {

        if (requestURL.contains(URL.REFRESH)) {
          throw new JwtException("Token is not expired yet");
        }

        UserDetails userDetails =
            new UserDetailsImpl(
                jwtProvider.getLoginFromToken(token),
                Strings.EMPTY,
                jwtProvider.getRolesFromToken(token));

        UsernamePasswordAuthenticationToken auth =
            new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(auth);
      }
    } catch (ExpiredJwtException e) {

      if (requestURL.contains(URL.REFRESH)) {

        if (jwtProvider.isRefreshAvailable(e)) {
          allowForRefreshToken(e, request);
        } else {
          request.setAttribute(
              Attributes.EXCEPTION, new JwtException("Token is not available for refresh yet"));
        }

      } else {
        request.setAttribute(Attributes.EXCEPTION, e);
      }

    } catch (JwtException e) {
      request.setAttribute(Attributes.EXCEPTION, e);
    }

    filterChain.doFilter(request, response);
  }

  private void allowForRefreshToken(ExpiredJwtException ex, HttpServletRequest request) {
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
        new UsernamePasswordAuthenticationToken(null, null, null);
    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    request.setAttribute(Attributes.CLAIMS, ex.getClaims());
  }

  private String getTokenFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader(Attributes.AUTHORIZATION);
    if (StringUtils.hasText(bearerToken)
        && bearerToken.startsWith(Attributes.TOKEN_BEGINNING_IN_HEADER)) {
      return bearerToken.replace(Attributes.TOKEN_BEGINNING_IN_HEADER, Strings.EMPTY);
    }
    return Strings.EMPTY;
  }
}
