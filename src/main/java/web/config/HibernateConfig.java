package web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@Configuration
@PropertySource("classpath:/prop.properties")
@EnableTransactionManagement
@ComponentScan("web")
public class HibernateConfig {

    @Autowired
    private Environment environment;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(environment.getProperty("prop.driver")));
        dataSource.setUrl(environment.getProperty("prop.url"));
        dataSource.setUsername(environment.getProperty("prop.user"));
        dataSource.setPassword(environment.getProperty("prop.pass"));
        return dataSource;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    Properties additionalProperties() {
        Properties propert = new Properties();
        propert.setProperty("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));
        propert.setProperty("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl.auto"));
        propert.setProperty("hibernate.dialect", environment.getProperty("hibernate.dialect"));
        return propert;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emfb
                = new LocalContainerEntityManagerFactoryBean();
        emfb.setDataSource(dataSource());
        emfb.setPackagesToScan("web.model");

        JpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        emfb.setJpaVendorAdapter(jpaVendorAdapter);
        emfb.setJpaProperties(additionalProperties());

        return emfb;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return transactionManager;
    }
}

