package com.abnamro.nl.favouriterecipe.jwt;

import com.abnamro.nl.favouriterecipe.enums.RecipesErrorEnum;
import com.abnamro.nl.favouriterecipe.exception.RecipesError;
import com.abnamro.nl.favouriterecipe.exception.RecipesException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author laskhmimadam
 *
 */

@Component
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenUtility jwtTokenUtility;

	@Autowired
	private JwtUserDetailsServiceImpl userDetailsService;

	private Set<String> skipUrls = new HashSet<>(Arrays.asList("/favourites/v1/authenticate", "/swagger-ui.html",
			"/swagger-ui/**", "/v3/api-docs", "/v3/api-docs/**"));

	private AntPathMatcher pathMatcher = new AntPathMatcher();

	@Value("${recipes.username:'recipesadmin'}")
	private String user;

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			var token = fetchTokenFromAuthHeader(request);
			var userName = jwtTokenUtility.getUsernameFromToken(token);
			if (userName.equalsIgnoreCase(user) && !jwtTokenUtility.isTokenExpired(token)) {
				var userDetails = userDetailsService.loadUserByUsername(userName);
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			} else {
				log.error("Authorization token is invalid for user :: {}", userName);
				SecurityContextHolder.clearContext();
				throw new RecipesException(RecipesErrorEnum.ERROR_INVALID_AUTH);
			}
			chain.doFilter(request, response);
		} catch (Exception exception) {
			log.error("Error while authorizing JWT :: {}", exception);
			var mapper = new ObjectMapper();
			RecipesError error = RecipesError.builder().code(RecipesErrorEnum.ERROR_INVALID_AUTH.getCode())
					.type(RecipesErrorEnum.ERROR_INVALID_AUTH.getType())
					.message(RecipesErrorEnum.ERROR_INVALID_AUTH.getMessage()).build();
			response.setStatus(HttpStatus.FORBIDDEN.value());
			response.getWriter().write(mapper.writeValueAsString(error));
			response.setContentType("application/json");
		}

	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		log.info("skip urls for filter for uri :: {}", request.getServletPath());
		return skipUrls.stream().anyMatch(path -> pathMatcher.match(path, request.getServletPath()));
	}

	/**
	 * This method is used to fetch token from authorization header
	 * 
	 * @param request
	 * @return
	 * @throws RecipesException
	 */
	private String fetchTokenFromAuthHeader(HttpServletRequest request) throws RecipesException {
		var authorizationHeader = request.getHeader("Authorization");
		if (StringUtils.isNotBlank(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
			return authorizationHeader.substring(7);
		} else {
			log.error("Authorizion header is blank or invalid");
			throw new RecipesException(RecipesErrorEnum.ERROR_INVALID_AUTH);
		}

	}

}
