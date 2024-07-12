package app;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.net.http.HttpHeaders;

@Component
@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080")
                .allowedMethods("GET", "PUT", "POST", "DELETE")
                .allowedHeaders(org.springframework.http.HttpHeaders.AUTHORIZATION, org.springframework.http.HttpHeaders.CONTENT_TYPE)
                .exposedHeaders(org.springframework.http.HttpHeaders.AUTHORIZATION, org.springframework.http.HttpHeaders.CONTENT_TYPE)
                .allowCredentials(true)
                .allowedHeaders("*");

        }


}

