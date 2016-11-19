package com.baziuk.spring.web;

import com.baziuk.spring.auditorium.config.AuditoriumServiceLayerConfig;
import com.baziuk.spring.booking.config.BookingServiceLayerConfig;
import com.baziuk.spring.data.H2DBConfig;
import com.baziuk.spring.discount.config.DiscountServiceLayerConfig;
import com.baziuk.spring.events.config.EventServiceLayerConfig;
import com.baziuk.spring.user.config.UserServiceLayerConfig;
import freemarker.template.TemplateException;
import freemarker.template.utility.XmlEscape;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Maks on 11/5/16.
 */
@Configuration
@EnableWebMvc
@ComponentScan({"com.baziuk.spring.*.web"})
@Import({H2DBConfig.class,
        AuditoriumServiceLayerConfig.class,
        EventServiceLayerConfig.class,
        UserServiceLayerConfig.class,
        BookingServiceLayerConfig.class,
        DiscountServiceLayerConfig.class})
@EnableAutoConfiguration
public class WebAppConfig extends WebMvcConfigurerAdapter {

    @Bean
    public FreeMarkerConfigurer freemarkerConfig() {
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setTemplateLoaderPath("/WEB-INF/views/");
        return configurer;
    }

    @Bean
    public ViewResolver freemarkerViewResolver() {
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
        resolver.setCache(false);
        resolver.setSuffix(".ftl");
        return resolver;
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        super.configureViewResolvers(registry);
        registry.viewResolver(pdfViewResolver());
    }

    @Bean
    public CommonsMultipartResolver multipartResolver(){
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("UTF-8");
        return resolver;
    }

    @Bean
    public PdfViewResolver pdfViewResolver(){
        PdfViewResolver pdfViewResolver = new PdfViewResolver();
        return pdfViewResolver;
    }
}
