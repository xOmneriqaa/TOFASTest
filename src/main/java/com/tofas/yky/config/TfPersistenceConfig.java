package com.tofas.yky.config;

import com.tofas.core.common.config.TfConstants;
import org.hibernate.ejb.HibernatePersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by T40127 on 12.11.2014.
 *
 * This bean holds the configuration about persistency and database connections.
 */

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories({"com.tofas.core.common.repository", "com.tofas.yky.repositories"})
public class TfPersistenceConfig {

	private @Autowired
	TfConstants tfConstants;

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	/**
	 * Returns the data source instance from the parameters defined in
	 * application.properties file. Development mode and live mode differs in
	 * reality.
	 *
	 * Development mode connects directly database with jdbc url. Production
	 * mode retrieves data source object that is defined in the application
	 * server with a specific jndi name.
	 *
	 * @return
	 */
	@Bean(name = "dataSource", destroyMethod = "")
	public DataSource getDataSource() {
		if (tfConstants.isApplicationIsInDevelopmentMode()) {
			DriverManagerDataSource ds = new DriverManagerDataSource(
					tfConstants.getJdbcUrl(), tfConstants.getJdbcName(),
					tfConstants.getJdbcPass());
			ds.setDriverClassName(tfConstants.getJdbcDriver());
			return ds;
		} else {
			JndiDataSourceLookup dsl = new JndiDataSourceLookup();
			dsl.setResourceRef(true);
			return dsl.getDataSource(tfConstants.getJdbcJndiName());
		}
	}

	@Bean
	public AbstractEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
		LocalContainerEntityManagerFactoryBean emFactory = new LocalContainerEntityManagerFactoryBean();

		emFactory.setDataSource(dataSource);
		emFactory.setPackagesToScan("com.tofas.core", "com.tofas.yky");
		emFactory.setPersistenceProviderClass(HibernatePersistence.class);
		emFactory.setJpaProperties(getHibernateProperties());
		emFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

		return emFactory;
	}

	private Properties getHibernateProperties() {
		Properties hp = new Properties();
		
		hp.setProperty("hibernate.dialect", tfConstants.getHibernateDialect());
		hp.setProperty("show_sql", tfConstants.getHibernateShowSql());
		
		return hp;
	}

	@Bean(name="transactionManager")
	public AbstractPlatformTransactionManager getTransactionManager(DataSource dataSource) {
		JpaTransactionManager jpaTran = new JpaTransactionManager();
		jpaTran.setEntityManagerFactory(entityManagerFactory);
		jpaTran.setDataSource(dataSource);
		return jpaTran;
	}

}
