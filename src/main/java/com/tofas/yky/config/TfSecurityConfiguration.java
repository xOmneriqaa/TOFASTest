package com.tofas.yky.config;

import com.tofas.core.common.config.TfConstants;
import com.tofas.core.common.security.TfAuthenticationProvider;
import com.tofas.core.restful.filter.TfCsrfCookieGeneratorFilter;
import com.tofas.core.restful.filter.TfRestfulAuthenticationEntryPoint;
import com.tofas.core.restful.security.TfRestLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;


/**
 * Created by T40127 on 12.11.2014.
 *
 * This configuration class holds the configuration about web site security;
 * authentication and authorization options.
 */

@Configuration
@EnableWebMvcSecurity
public class TfSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    Environment env;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private TfRestfulAuthenticationEntryPoint tfRestfulAuthenticationEntryPoint;

    @Autowired
    private TfRestLogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    private TfAuthenticationProvider authenticationProvider;
    
    @Autowired
    private TfConstants tfConstants;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }

    /**
     * This method is responsible for configuring some authentication options and
     * all of the authorization configuration.
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //TODO filter düzenlemesi
    	// Authorize basic configuration and DEVELOPER role configuration
        http
            //.addFilterBefore(new TfAnonymousUserFilter(), CsrfFilter.class)
            .addFilterAfter(new TfCsrfCookieGeneratorFilter(), CsrfFilter.class)
            .authorizeRequests()
                .antMatchers(TfConstants.DEVELOPER_URL_MASK).hasAuthority(tfConstants.getDeveloperRole())
                .and()
                .logout()
                .logoutUrl("/login/api/logout")
                .logoutSuccessHandler(logoutSuccessHandler)
                .deleteCookies("JSESSIONID")
                .permitAll()
            .and()
                .httpBasic().authenticationEntryPoint(tfRestfulAuthenticationEntryPoint)
            .and()
                .headers()
                    .addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN));

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public TfAuthenticationProvider getTofasAuthenticationProvider() {
        return new TfAuthenticationProvider();
    }


}
