package com.tofas.yky.config;

import com.tofas.core.common.config.TfConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import javax.mail.Session;
import java.util.Properties;

/**
 * Created by T40127 on 12.11.2014.
 *
 * This is the application wide configuration class which holds bean definitions about
 * resources, templating, view-resolvers, message-resolvers, file resolver (file upload).
 *
 *
 */

@PropertySources({
        @PropertySource("classpath:application.properties"),
        @PropertySource(value = "file:${com.tofas.appFolder}/yky/config/application.properties", ignoreResourceNotFound = true)
})
@Configuration
@ComponentScan(basePackages = {"com.tofas.yky", "com.tofas.core.common", "com.tofas.core.restful"},
    excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = {"com.tofas.yky.controller.*",
            "com.tofas.core.restful.controllers.*", "com.tofas.core.common.controllers"}))
//@EnableWebMvc
@EnableCaching
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableScheduling
public class TfApplicationConfig  {

    private static Logger logger = LoggerFactory.getLogger(TfApplicationConfig.class);

    private @Autowired
    TfConstants tfConstants;


    /**
     * This bean is intentionally defined as static for accessing application.properties file contents.
     *
     * @return
     */
    @Bean(name = "propertySource")
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean(name = "javaMailSession")
    public Session javaMailSession(){
        Properties properties = new Properties();
        properties.put("mail.smtp.host", tfConstants.getMailSmtpHost());
        properties.put("mail.smtp.socketFactory.port", tfConstants.getMailSocketFactoryPort());
        properties.put("mail.smtp.socketFactory.class",tfConstants.getMailSocketFactoryClass());
        properties.put("mail.smtp.auth", tfConstants.getMailSmtpAuth());
        properties.put("mail.smtp.port", tfConstants.getMailSmtpPort());
        return Session.getDefaultInstance(properties);
    }

    @Bean(name = "cacheManager")
    public EhCacheCacheManager cacheManager() {
        return new EhCacheCacheManager(ehCacheManagerFactoryBean().getObject());
    }


    @Bean(name = "ehcacheFactory")
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
        EhCacheManagerFactoryBean ehcacheBean = new EhCacheManagerFactoryBean();
        ehcacheBean.setShared(true);
        ehcacheBean.setAcceptExisting(true);
        ehcacheBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
        return ehcacheBean;
    }


    @Bean(name = "mailSender")
    public JavaMailSender getMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(tfConstants.getMailSmtpHost());
        mailSender.setPort(Integer.parseInt(tfConstants.getMailSmtpPort()));
        mailSender.setDefaultEncoding("UTF-8");

        return mailSender;
    }




}
