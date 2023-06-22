package ua.edu.ukma.cinemax.security.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order()
public class FilmDetailsAuthFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(FilmDetailsAuthFilter.class);
    private static final RequestMatcher URI_MATCHER =
            new AntPathRequestMatcher("/film/details/**", HttpMethod.GET.name());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext context = filterConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        if (URI_MATCHER.matches(req)) {
            String[] path = req.getRequestURI().split("/");
            int id = Integer.parseInt(path[path.length - 1]);
            LOGGER.info("Filtered out");
            Object auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null) {
                LOGGER.info("NULL");
                res.sendRedirect("/film/" + id);
            } else {
                LOGGER.info(auth.toString());
                chain.doFilter(request, response);
            }
        } else {
            chain.doFilter(request, response);
        }
    }

}
