package com.digi.banksystem.config;

import com.digi.banksystem.util.MD5Encoder;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BeanConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.tomcat")
    public DataSource getDataSource(){
        return new DataSource();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new MD5Encoder();
    }
}
