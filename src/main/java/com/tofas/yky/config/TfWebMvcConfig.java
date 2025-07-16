package com.tofas.yky.config;
/* T40127 @ 16.11.2015. */

import com.tofas.core.common.message.TfMessageResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.extras.springsecurity3.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import java.util.HashSet;

@Configuration
@ComponentScan(basePackages = {"com.tofas.yky.controller", "com.tofas.core.restful.controllers", "com.tofas.core.common.controllers"})
@EnableWebMvc
public class TfWebMvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private SpringTemplateEngine templateEngine;


    /**
     * Adds resource folder -> url match for static resources
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
        registry.addResourceHandler("/ng/view/**").addResourceLocations("/WEB-INF/views/");
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }


    /**
     *Generates template engine bean. There are two chained template engines
     * One is used for basic/ready templates that has been hold in the jar file.
     * The other is used for lively editable templates that has been edited in the project itself.
     *
     * @return
     */
    @Bean
    public SpringTemplateEngine getTemplateEngine() {
        // initialize resolvers
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setTemplateMode("HTML5");
        resolver.setCharacterEncoding("UTF-8");
        resolver.setCacheable(false);
        resolver.setSuffix(".html");
        resolver.setPrefix("/views/");
        resolver.setOrder(2);

        ServletContextTemplateResolver str = new ServletContextTemplateResolver();
        str.setPrefix("/WEB-INF/views/");
        str.setSuffix(".html");
        str.setTemplateMode("HTML5");
        str.setCharacterEncoding("UTF-8");
        str.setCacheable(false);
        str.setOrder(1);

        // initialize engine
        SpringTemplateEngine ste = new SpringTemplateEngine();
        ste.addTemplateResolver(str);
        ste.addTemplateResolver(resolver);

        HashSet<IDialect> dialects = new HashSet<>();
        dialects.add(new SpringSecurityDialect());
        ste.setAdditionalDialects(dialects);

        return ste;
    }

    /**
     * Generates view resolver for thymeleaf templating
     *
     * @return
     */
    @Bean
    public ThymeleafViewResolver getViewResolver() {
        ThymeleafViewResolver tvr = new ThymeleafViewResolver();
        tvr.setTemplateEngine(templateEngine);
        tvr.setCharacterEncoding("UTF-8");
        tvr.setCache(false);
        return tvr;
    }

    /**
     *
     * Creates message source definition beans.
     * First one is used to read message files in the library jar file which is distributed with library
     * The other one is developed within the project and hold in the project source files.
     *
     * @return
     */
    @Bean(name = "messageSource")
    public MessageSource getMessageSource() {
        TfMessageResource msgSrc = new TfMessageResource();
        msgSrc.setBasename("classpath:i18n/message-all");
        msgSrc.setDefaultEncoding("UTF-8");
        msgSrc.setCacheSeconds(1);

        TfMessageResource msgSrcChild = new TfMessageResource();
        msgSrcChild.setBasename("classpath:i18n/message");
        msgSrcChild.setDefaultEncoding("UTF-8");
        msgSrcChild.setCacheSeconds(1);
        msgSrcChild.setParentMessageSource(msgSrc);

        return msgSrcChild;
    }

    /**
     * This bean is used to manage the multi-part form datas, for uploading files to server side.
     *
     * @return
     */
    @Bean(name="filterMultipartResolver")
    public CommonsMultipartResolver getMultipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSize(-1);
        return resolver;
    }

}
