package com.green.bloom.mybatis;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class MyBatisConfig {
	
	private final DataSource dataSource;//jpa 같이쓰면 별도 bean 생설할 필요없다
	
	private final ApplicationContext application;
	
	@Bean
	SqlSessionTemplate sqlSession() throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory());
	}

	@Bean
	SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		log.debug(">>>>>dataSource: "+dataSource);
		factoryBean.setDataSource(dataSource);
		//mapper interface-DAO(DataAccessObject)
		String locationPattern="classpath:/static/mapper/**/*-mapper.xml";//패턴에적용할 xml저장위치
		Resource[] mapperLocations=application.getResources(locationPattern);
		factoryBean.setMapperLocations(mapperLocations);// xml파일 위치
		
		factoryBean.setConfiguration(mybatisConfig());
		return factoryBean.getObject();
	}

	@ConfigurationProperties(prefix = "mybatis.configuration")
	@Bean
	org.apache.ibatis.session.Configuration mybatisConfig() {
		return new org.apache.ibatis.session.Configuration();
	}

}
