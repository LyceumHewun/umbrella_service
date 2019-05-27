package cc.lyceum.umbrella.config;

import cc.lyceum.umbrella.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author lamze
 * @version 1.0
 * @date 2019-04-14 05:03
 */

@SpringBootConfiguration
public class ConfigInterceptor extends WebMvcConfigurerAdapter {

    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor).addPathPatterns("/**");
//        registry.addInterceptor(new AuthInterceptor())
//                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**", "/swagger-ui.html", "/error");
        super.addInterceptors(registry);
    }
}