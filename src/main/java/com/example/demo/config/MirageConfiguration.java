/*
 * (c) 2016 Prismatix, Inc.
 *
 * NOTICE:  All source code, documentation and other information
 * contained herein is, and remains the property of Prismatix, Inc.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Prismatix, Inc.
 */
package com.example.demo.config;

import com.example.demo.DemoApplication;
import com.miragesql.miragesql.SqlManagerImpl;
import com.miragesql.miragesql.bean.BeanDescFactory;
import com.miragesql.miragesql.bean.FieldPropertyExtractor;
import com.miragesql.miragesql.dialect.MySQLDialect;
import com.miragesql.miragesql.integration.spring.SpringConnectionProvider;
import com.miragesql.miragesql.naming.RailsLikeNameConverter;
import com.miragesql.miragesql.provider.ConnectionProvider;
import jp.xet.springframework.data.mirage.repository.config.EnableMirageRepositories;
import jp.xet.springframework.data.mirage.repository.support.MiragePersistenceExceptionTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Spring Data Mirage configuration.
 *
 */
@Configuration
@EnableTransactionManagement
@EnableMirageRepositories(basePackageClasses = DemoApplication.class, //
		basePackages = {
			"com.example.demo"
		}, sqlManagerRef = "sqlManager")
@SuppressWarnings("javadoc")
public class MirageConfiguration {
	
	@Autowired
	DataSource dataSource;
	
	
	@Bean
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource);
	}
	
	@Bean
	public SqlManagerImpl sqlManager() {
		SqlManagerImpl sqlManagerImpl = new SqlManagerImpl();
		sqlManagerImpl.setCacheMode(true);
		sqlManagerImpl.addValueType(new JacksonSerializedValueType());
		sqlManagerImpl.setConnectionProvider(connectionProvider());
		sqlManagerImpl.setDialect(new MySQLDialect());
		sqlManagerImpl.setBeanDescFactory(beanDescFactory());
		sqlManagerImpl.setNameConverter(new RailsLikeNameConverter());
		return sqlManagerImpl;
	}
	
	@Bean
	public ConnectionProvider connectionProvider() {
		SpringConnectionProvider springConnectionProvider = new SpringConnectionProvider();
		springConnectionProvider.setDataSource(dataSource);
		return springConnectionProvider;
	}
	
	@Bean
	public BeanDescFactory beanDescFactory() {
		BeanDescFactory beanDescFactory = new BeanDescFactory();
		beanDescFactory.setPropertyExtractor(new FieldPropertyExtractor());
		beanDescFactory.setCacheEnabled(true);
		return beanDescFactory;
	}
	
	@Bean
	public MiragePersistenceExceptionTranslator persistenceExceptionTranslator() {
		return new MiragePersistenceExceptionTranslator();
	}
}
