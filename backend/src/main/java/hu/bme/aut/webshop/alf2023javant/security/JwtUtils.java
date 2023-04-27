package hu.bme.aut.webshop.alf2023javant.security;

import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtils {

	// @Value("${javant.app.jwtSecret}")
	private String jwtSecret = "SECRET";

	// @Value("${javant.app.jwtExpirationMs}")
	private int jwtExpirationMs = 86400000;

	public String generateJwtToken(Authentication authentication) {

		UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

		return Jwts.builder()
				.setSubject((userPrincipal.getUsername()))
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}

	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			// logger.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			// logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			// logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			// logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			// logger.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}
}