//package vnpost.technology.utils;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//import vnpost.technology.service.JwtTokenService;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Component
//@Slf4j// log
//public class JwtTokenFilter extends OncePerRequestFilter {
//
//	@Autowired
//	private JwtTokenService jwtTokenService;
//
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//		String token = resolveToken(request);
//		if (token != null) {
//			String username = jwtTokenService.getUsername(token);
//			if (username != null) {
//				Authentication auth = jwtTokenService.getAuthentication(username);
//				SecurityContextHolder.getContext().setAuthentication(auth);
//			} else {
//				SecurityContextHolder.clearContext();
//				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No Authorization");
//				return;
//			}
//		}
//		filterChain.doFilter(request, response);//filterChain để cho requset đi >< khoonng sẽ dừng lại
//	}
//
//	private String resolveToken(HttpServletRequest request) {
//		String bearerToken = request.getHeader("Authorization");
//		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
//			return bearerToken.substring(7);//cắt "Bearer " để lấy token xác minh
//		}
//		return null;
//	}
//}
