package com.itheima.config;

import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * MyBatis 配置类：配置 SqlSessionFactoryBean（数据源、分页插件、Mapper 映射文件）
 * 以及 MapperScannerConfigurer（扫描 com.itheima.mapper 接口）
 */
@Configuration
public class MyBatisConfig {

    //配置分页插件 PageHelper
    @Bean
    public PageInterceptor getPageInterceptor() {
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("helperDialect", "mysql");
        properties.setProperty("reasonable", "true");
        properties.setProperty("supportMethodsArguments", "true");
        pageInterceptor.setProperties(properties);
        return pageInterceptor;
    }

    @Bean
    public SqlSessionFactoryBean getSqlSessionFactoryBean(@Autowired DataSource dataSource,
                                                          @Autowired PageInterceptor pageInterceptor) throws Exception {
        SqlSessionFactoryBean ssfb = new SqlSessionFactoryBean();
        //设置数据源
        ssfb.setDataSource(dataSource);
        //设置分页插件
        ssfb.setPlugins(new Interceptor[]{pageInterceptor});
        //设置Mapper接口对应的映射文件位置
        ssfb.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath*:com/itheima/mapper/*.xml"));
        return ssfb;
    }

    @Bean
    public static MapperScannerConfigurer getMapperScannerConfigurer() {
        MapperScannerConfigurer msc = new MapperScannerConfigurer();
        //扫描Mapper接口所在的包
        msc.setBasePackage("com.itheima.mapper");
        return msc;
    }
}
