package com.apex.web.security;

import static com.apex.web.security.Constants.DEFAULT_CSRF_HEADER_NAME;
import static com.apex.web.security.Constants.STRING_ENCRYPTOR_NAME;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.jasypt.hibernate4.encryptor.HibernatePBEStringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.hateoas.config.EnableEntityLinks;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.apex.web.security.authetication.SuccessForwarderHandler;
import com.apex.web.security.authetication.SuccessLogoutHandler;
import com.apex.web.security.authetication.filter.CSRFFilter;
import com.apex.web.security.authetication.thirdparty.LogiAnalyticSecurityKeyHandler;
import com.apex.web.security.validation.TicketValidator;

@EnableAsync
@EnableEntityLinks
@EnableScheduling
@SpringBootApplication
public class Application {

    @Value("spring.messages.basename")
    private String messageSourceBasename;

    @Value("spring.messages.encoding")
    private String messageEncoding;

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
	ReloadableResourceBundleMessageSource messageBundle = new ReloadableResourceBundleMessageSource();
	messageBundle.setBasename(messageSourceBasename);
	messageBundle.setDefaultEncoding(messageEncoding);
	return messageBundle;
    }

    @Configuration
    public static class MvcConfig extends WebMvcConfigurerAdapter {
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.config.annotation.
	 * WebMvcConfigurerAdapter#addViewControllers(org.springframework.web.
	 * servlet.config.annotation.ViewControllerRegistry)
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
	    registry.addViewController("/home").setViewName("home");
	    registry.addViewController("/").setViewName("home");
	    registry.addViewController("/hello").setViewName("hello");
	    registry.addViewController("/login").setViewName("login");
	}

    }

    private @Autowired TicketValidator simpleTicketValidator;

    @Bean
    public List<TicketValidator> validationChain() {
	List<TicketValidator> validationChain = new ArrayList<>();
	validationChain.add(simpleTicketValidator);
	return validationChain;
    }

    /**
     * 
     * @author hitokiri
     *
     */
    @Configuration
    public static class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private @Autowired LogiAnalyticSecurityKeyHandler logiAnalyticSecurityKeyHandler;
	private @Autowired SuccessForwarderHandler successForwarderHandler;
	private @Autowired AuthenticationProvider jpaAuthenticationProvider;
	private @Autowired SuccessLogoutHandler successLogoutHandler;
	private @Autowired CSRFFilter csrfFilter;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.config.annotation.web.configuration.
	 * WebSecurityConfigurerAdapter#configure(org.springframework.security.
	 * config.annotation.web.builders.HttpSecurity)
	 */
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
	    httpSecurity.authorizeRequests().antMatchers("/", "/home")
		    .permitAll().anyRequest().authenticated().and().csrf()
		    .csrfTokenRepository(csrfTokenRepository()).and()
		    .addFilterAfter(csrfFilter, CsrfFilter.class)
		    .authenticationProvider(jpaAuthenticationProvider)
		    .formLogin().loginPage("/login").permitAll()
		    .successHandler(logiAnalyticSecurityKeyHandler)
		    .successHandler(successForwarderHandler).and().logout()
		    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		    .logoutSuccessHandler(successLogoutHandler).permitAll();

	}

	/**
	 * 
	 * @return
	 */
	private CsrfTokenRepository csrfTokenRepository() {
	    HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
	    repository.setHeaderName(DEFAULT_CSRF_HEADER_NAME);
	    return repository;
	}

    }

    /**
     * 
     * @author hitokiri
     *
     */
    @EnableCaching
    @EnableAutoConfiguration
    @EnableTransactionManagement
    @EntityScan(basePackageClasses = { RepositoryConfiguration.class,
	    Jsr310JpaConverters.class })
    public static class RepositoryConfiguration {

	@Value("application.string.encryptor.password")
	private String stringEncryptorPassword;

	@Value("spring.cache.ehcache.config")
	private String ehcacheConfigLocation;

	@Bean
	public HibernatePBEStringEncryptor hibernateStringEncryptor() {
	    HibernatePBEStringEncryptor encryptor = new HibernatePBEStringEncryptor();
	    encryptor.setRegisteredName(STRING_ENCRYPTOR_NAME);
	    encryptor.setPassword(stringEncryptorPassword);
	    return encryptor;
	}

	@Bean
	public CacheManager cacheManager() {
	    EhCacheManagerFactoryBean cacheManager = new EhCacheManagerFactoryBean();
	    cacheManager.setConfigLocation(
		    new ClassPathResource(ehcacheConfigLocation));
	    cacheManager.setShared(true);
	    return new EhCacheCacheManager(cacheManager.getObject());
	}

    }

    /**
     * 
     * @return
     */
    @Bean
    public CloseableHttpClient HttpClient() {
	PoolingHttpClientConnectionManager httpConnectionPool = new PoolingHttpClientConnectionManager();
	httpConnectionPool.setDefaultMaxPerRoute(20);
	return HttpClients.custom().setConnectionManager(httpConnectionPool)
		.build();
    }

    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
	SpringApplication.run(Application.class, args);
    }

}