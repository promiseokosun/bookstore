package com.interswitch.bookstore;

import lombok.extern.slf4j.Slf4j;
import org.junit.ClassRule;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest
@ActiveProfiles("test")
@Slf4j
@ContextConfiguration(initializers = BookStoreApplicationTests.Initializer.class)
public abstract class BookStoreApplicationTests {

	@ClassRule
	public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:14-alpine")
			.withDatabaseName("bookstore");

	static {
		postgreSQLContainer.start();
	}

	public static class Initializer
			implements ApplicationContextInitializer<ConfigurableApplicationContext> {
		public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
			TestPropertyValues.of(
					"spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
					"spring.datasource.username=" + postgreSQLContainer.getUsername(),
					"spring.datasource.password=" + postgreSQLContainer.getPassword(),
					"spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect",
					"spring.jpa.show-sql=false",
					"spring.jpa.hibernate.ddl-auto=update"
			).applyTo(configurableApplicationContext.getEnvironment());
		}
	}
}
