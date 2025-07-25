package com.njglyy.corporate_group_backend.config.databaseConfig;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.njglyy.corporate_group_backend.mapper", sqlSessionTemplateRef = "corporateGroupSqlSessionTemplate")

public class CorporateGroupDataSourceConfig {
    @Bean(name = "corporateGroupDataSource")
    @ConfigurationProperties(prefix = "corporategroup.spring.datasource")
    @Primary
    public DataSource doctororderDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "corporateGroupSqlSessionFactory")
    @Primary
    public SqlSessionFactory doctororderSqlSessionFactory(@Qualifier("corporateGroupDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "corporateGroupTransactionManager")
    @Primary
    public DataSourceTransactionManager corporateGroupTransactionManager(@Qualifier("corporateGroupDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "corporateGroupSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate corporateGroupSqlSessionTemplate(@Qualifier("corporateGroupSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
