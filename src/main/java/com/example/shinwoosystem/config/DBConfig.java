package com.example.shinwoosystem.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
//@MapperScan(basePackages = {"com.example.secondproject.mybatis"}) // Mapper 인터페이스를 스캔하여 Bean에 등록
public class DBConfig {
    @Bean   // Spring에서 관리하는 저장소인 Bean에 등록(jpa)
    public DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariConfig.setJdbcUrl("jdbc:mysql://1.220.247.78:3307/final_2405_team1");
        hikariConfig.setUsername("final_2405_team1_user");
        hikariConfig.setPassword("1234");

        hikariConfig.setPoolName("wish-pool");
        hikariConfig.setMaximumPoolSize(3);

        return new HikariDataSource(hikariConfig);
    }
    



}
