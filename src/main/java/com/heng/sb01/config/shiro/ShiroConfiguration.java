package com.heng.sb01.config.shiro;

import net.sf.ehcache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfiguration {

    @Bean
    public ShiroFilterFactoryBean shiroFilter() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/**", "all");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("all", new MyShiroFilter());
        shiroFilterFactoryBean.setFilters(filterMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        securityManager.setSessionManager(sessionManager());
        securityManager.setCacheManager(ehCacheManager());
        securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }

    @Bean
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setGlobalSessionTimeout(60 * 30 * 1000);
        sessionManager.setSessionDAO(sessionDAO());
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionValidationInterval(60 * 1000);
        return sessionManager;
    }

    @Bean
    public SessionDAO sessionDAO() {
        EnterpriseCacheSessionDAO sessionDAO = new EnterpriseCacheSessionDAO();
        sessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
        sessionDAO.setCacheManager(ehCacheManager());
        return sessionDAO;
    }

    @Bean
    public EhCacheManager ehCacheManager() {
        EhCacheManager ehCacheManager = new EhCacheManager();
        CacheManager cacheManager = CacheManager.create(net.sf.ehcache.config.ConfigurationFactory.parseConfiguration());
        ehCacheManager.setCacheManager(cacheManager);
        return ehCacheManager;
    }

    @Bean
    public MyShiroRealm myShiroRealm() {
        return new MyShiroRealm();
    }

    @Bean
    public SimpleCookie simpleCookie() {
        SimpleCookie cookie = new SimpleCookie();
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60 * 60 * 24);
        return cookie;
    }

    @Bean
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
        cookieRememberMeManager.setCookie(simpleCookie());
        return cookieRememberMeManager;
    }

}
