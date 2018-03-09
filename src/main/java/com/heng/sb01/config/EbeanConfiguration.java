package com.heng.sb01.config;

import com.heng.sb01.entity.BaseModel;
import io.ebean.EbeanServer;
import io.ebean.EbeanServerFactory;
import io.ebean.config.CurrentUserProvider;
import io.ebean.config.ServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.sql.DataSource;
import java.util.List;

@Configuration
@Order(2)
public class EbeanConfiguration {
    @Autowired
    private ApplicationContext applicationContext;

    @Value("${spring.ebean.ddl.run}")
    private Boolean ddlRun;
    @Value("${spring.ebean.ddl.generate}")
    private Boolean ddlGenerate;
    @Value("${spring.ebean.default.server}")
    private Boolean defaultServer;
    @Value("#{'${spring.ebean.packages}'.split(',')}")
    private List<String> packages;

    @Bean
    public EbeanServer ebeanServer() {
        ServerConfig serverConfig = new ServerConfig();
        DataSource dataSource = applicationContext.getBean(DataSource.class);
        serverConfig.setDataSource(dataSource);
        serverConfig.setDdlGenerate(ddlGenerate);
        serverConfig.setDdlRun(ddlRun);
        serverConfig.setPackages(packages);
        serverConfig.setCurrentUserProvider(currentUserProvider());
        return EbeanServerFactory.create(serverConfig);
    }

    @Bean
    public CurrentUserProvider currentUserProvider() {
        return new BaseModel.UserProvider();
    }
}
