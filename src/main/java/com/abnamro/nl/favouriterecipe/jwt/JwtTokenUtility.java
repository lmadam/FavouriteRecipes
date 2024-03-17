package com.abnamro.nl.favouriterecipe.jwt;

import com.abnamro.nl.favouriterecipe.exception.RecipesException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

/**
 * @author laskhmimadam
 *
 */

@Component
public class JwtTokenUtility {

	@Value("${jwt.secret:'recipes_jwt_secret'}")
	private String secret;

	@Value("${jwt.expiry:18000000}")
	private long tokenExpiry;

	public String generateToken(UserDetails userDetails) throws RecipesException {

		var algorithm = SignatureAlgorithm.HS256;
		var claims = Jwts.claims().setSubject(userDetails.getUsername());
		var issueDate = new Date();
		var expiryDate = new Date(issueDate.getTime() + tokenExpiry);
		return Jwts.builder().setClaims(claims).setIssuedAt(issueDate).setExpiration(expiryDate)
				.signWith(algorithm, secret).compact();

	}

	/**
	 * This method is used to fetch user name from token
	 * @param token
	 * @return
	 * @throws RecipesException
	 */
	public String getUsernameFromToken(String token) throws RecipesException {
		return getClaimFromToken(token, Claims::getSubject);
	}

	/**
	 * This method is used to fetch expire date from token
	 * @param token
	 * @return
	 * @throws RecipesException
	 */
	public Date getTokenExpiry(String token) throws RecipesException {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	/**
	 * This method is used to load specific claim from token
	 * @param <T>
	 * @param token
	 * @param claimsResolver
	 * @return
	 * @throws RecipesException
	 */
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) throws RecipesException{
		final var claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	/**
	 * This method is used to fetch all token claims
	 * @param token
	 * @return
	 */
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	/**
	 * This method is used to check if JWT expired or not
	 * @param token
	 * @return
	 * @throws RecipesException 
	 */
	public boolean isTokenExpired(String token) throws RecipesException {
		final var expiration = getTokenExpiry(token);
		return expiration.before(new Date());
	}

}
