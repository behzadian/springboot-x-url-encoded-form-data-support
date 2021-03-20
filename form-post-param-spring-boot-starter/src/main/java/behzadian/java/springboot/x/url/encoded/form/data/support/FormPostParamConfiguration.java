package behzadian.java.springboot.x.url.encoded.form.data.support;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.annotation.WebFilter;

/**
 * Used to config application for supporting FormPostParam
 */
@Configuration
@ConditionalOnClass(SimpleFormArgumentResolver.class)
public class FormPostParamConfiguration {

    @Bean
    public ContentCachingFilter filter(){
        return new ContentCachingFilter();
    }

    @Bean
    public FormPostParamWebConfig handler(){
        return new FormPostParamWebConfig();
    }
}
