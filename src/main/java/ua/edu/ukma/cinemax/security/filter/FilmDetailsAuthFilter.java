package ua.edu.ukma.cinemax.security.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order()
public class FilmDetailsAuthFilter implements Filter {
    private ServletContext context;
    final static Logger logger = LoggerFactory.getLogger(FilmDetailsAuthFilter.class);
    private static final RequestMatcher uriMatcher =
            new AntPathRequestMatcher("/film/details/**", HttpMethod.GET.name());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.context = filterConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        if (uriMatcher.matches(req)) {
            String[] path = req.getRequestURI().split("/");
            Integer id = Integer.parseInt(path[path.length-1]);
            logger.info("Filtered out");
            Object auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null) {
                logger.info("NULL");
                res.sendRedirect("/film/"+id);
            } else {
                logger.info(auth.toString());
                chain.doFilter(request,response);
            }
        } else {
            chain.doFilter(request,response);
        }
    }

}
