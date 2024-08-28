package ru.mkilord.tacos.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry reg) {
        reg.addViewController("/").setViewName("home");
        reg.addViewController("/test");
        reg.addViewController("/about_us");
        reg.addViewController("/logout_page");
        reg.addViewController("/order_created").setViewName("order_created");
    }
}
