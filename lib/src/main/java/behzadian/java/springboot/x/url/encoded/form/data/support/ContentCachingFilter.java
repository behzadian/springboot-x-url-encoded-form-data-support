package behzadian.java.springboot.x.url.encoded.form.data.support;

import org.apache.commons.logging.LogFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

/**
 * Used to cache request body if request if of type `x-url-encoded-form-data`
 */
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@Component
@WebFilter(filterName = "ContentCachingFilter", urlPatterns = "/*")
public class ContentCachingFilter extends OncePerRequestFilter {
    protected final org.apache.commons.logging.Log logger = LogFactory.getLog(this.getClass());

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String contentType = httpServletRequest.getContentType();
        /*
         * Sometimes browser adds charset at the end of content type header, so we will check only starting
         */
        if (contentType == null || !contentType.toLowerCase(Locale.ROOT).startsWith(MediaType.APPLICATION_FORM_URLENCODED_VALUE)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } else {
            CachedBodyHttpServletRequest cachedBodyHttpServletRequest = new CachedBodyHttpServletRequest(httpServletRequest);
            filterChain.doFilter(cachedBodyHttpServletRequest, httpServletResponse);
        }
    }
}