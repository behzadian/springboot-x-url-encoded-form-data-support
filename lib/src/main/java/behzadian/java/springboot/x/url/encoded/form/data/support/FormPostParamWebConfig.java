package behzadian.java.springboot.x.url.encoded.form.data.support;

import behzadian.java.springboot.x.url.encoded.form.data.support.SimpleFormArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * adds handler method resolver that extracts data from post body
 */
@Configuration
public class FormPostParamWebConfig implements WebMvcConfigurer {

    /**
     * adds handler method resolver that extracts data from post body
     * @param argumentResolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new SimpleFormArgumentResolver());
    }
}
