//package vnpost.technology.service;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.stereotype.Service;
//
//import java.util.Collection;
//import java.util.Date;
//
//@Service
//public class JwtTokenService {
//	@Value("${jwt.secret:123456}")
//	private String secretKey;
//
//	private long validityInMilliseconds = 3600000; // 1h
//
//	@Autowired
//	UserDetailsService userDetailsService;
//
//
//	public String createToken(String username, Collection<? extends GrantedAuthority> authorities) {
//		Claims claims = Jwts.claims().setSubject(username);
//		Date now = new Date(); // thời gian bây giờ
//		Date expiredTime = new Date(now.getTime() + validityInMilliseconds); // thời gian hết hạn
//		String accessToken = Jwts.builder()
//							.claim("role",authorities)
//				             .setSubject(username)
//							.setIssuedAt(now)
//							.setExpiration(expiredTime)
//							.signWith(SignatureAlgorithm.HS256, secretKey) // thuật toán
//							.compact();
//		return accessToken;
//	}
//
//	public boolean validateToken(String token) {
//		try {
//		Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token); // check token còn hạn hay không
//		return true;
//		} catch (Exception e) {
//			return false;
//		}
//	}
//	public String getUsername(String token) {
//		try {
//		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
//				.getBody().getSubject();
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
//	public Authentication getAuthentication(String username) {
//		UserDetails userDetails = userDetailsService.
//				loadUserByUsername((username));
//		return new UsernamePasswordAuthenticationToken(userDetails, "",
//				userDetails.getAuthorities());
//	}
//}
