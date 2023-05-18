package ru.job4j.todo.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
public class ConfigStyles implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/static/styles/**")
                .addResourceLocations("classpath:/static/styles/");
        registry
                .addResourceHandler("/task/static/styles/**")
                .addResourceLocations("classpath:/static/styles/");
        registry
                .addResourceHandler("/modify/static/styles/**")
                .addResourceLocations("classpath:/static/styles/");
    }
}
