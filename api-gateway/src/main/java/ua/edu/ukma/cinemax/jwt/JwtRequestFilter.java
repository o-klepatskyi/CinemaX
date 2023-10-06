package ua.edu.ukma.cinemax.jwt;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;
import ua.edu.ukma.cinemax.security.UserDetailsServiceImpl;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserDetailsServiceImpl jwtUserDetailsService;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        String jwt = jwtUtil.getJWTFromRequest(request);

        if (StringUtils.hasText(jwt) && jwtUtil.validateToken(jwt)) {
//            String username = jwtUtil.extractUsername(jwt);
//            UserDetails userDetails = // Load user details from your user service
//            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//
//            filterChain.doFilter(request, response);
        }
    }

    private String getServiceURLBasedOnRequest(HttpServletRequest request) {
        String path = request.getRequestURI();

        if (path.startsWith("/hello")) {
            return "http://localhost:8081/hello";
        } else if (path.startsWith("/cinema-hall")) {
            return "http://cinema-hall-service";
        }

        throw new IllegalArgumentException("Unknown service for path: " + path);
    }

    private void forwardRequestToService(String serviceURL,
                                         HttpServletRequest originalRequest,
                                         HttpServletResponse originalResponse) throws IOException {
        String jwt = jwtUtil.getJWTFromRequest(originalRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + jwt);

        // Copy the request body if necessary (e.g., for POST requests)
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                serviceURL + originalRequest.getRequestURI(),
                HttpMethod.resolve(originalRequest.getMethod()),
                entity,
                String.class);

        // Forward the response to the client
        originalResponse.setStatus(response.getStatusCodeValue());
        originalResponse.getWriter().write(response.getBody());
    }
}