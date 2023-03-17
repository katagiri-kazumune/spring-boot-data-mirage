/*
 * (c) 2016 Prismatix, Inc.
 *
 * NOTICE:  All source code, documentation and other information
 * contained herein is, and remains the property of Prismatix, Inc.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Prismatix, Inc.
 */
package com.example.demo.model;

import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import com.example.demo.config.MirageConfiguration;
import org.flywaydb.test.FlywayTestExecutionListener;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TODO
 * 
 */
@TestExecutionListeners({
	DependencyInjectionTestExecutionListener.class,
	TransactionalTestExecutionListener.class,
	FlywayTestExecutionListener.class
})
@ContextConfiguration(classes = {
	DataSourceAutoConfiguration.class,
		MirageConfiguration.class,
	FlywayAutoConfiguration.class
}, initializers = ConfigDataApplicationContextInitializer.class)
@TestPropertySource("/application-unittest.properties")
@Transactional
@Rollback

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RepositoryUnitTest {
}
