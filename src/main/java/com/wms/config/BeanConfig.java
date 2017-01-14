package com.wms.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by duyot on 8/23/2016.
 */
@Configuration
public class BeanConfig {

    Logger log = LoggerFactory.getLogger(BeanConfig.class);

    /*
        config profile in web.xml, param name: spring.profiles.default
     */
    @Bean
    @Primary
    @Profile(value = "dev")
    @Lazy
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("oracle.jdbc.OracleDriver");
        ds.setUrl("jdbc:oracle:thin:@10.84.70.116:1521:DEVMKT");
        ds.setUsername("sms_ads");
        ds.setPassword("sms123");
        log.info("Getting datasource with dev profile");
        return ds;
    }

    @Bean
    @Profile(value = "prod")
    @Lazy
    public JndiObjectFactoryBean initDataSourceFromJndi() {
        JndiObjectFactoryBean jndiObjectFB = new JndiObjectFactoryBean();
        jndiObjectFB.setJndiName("jdbc/ApplicationPool");
        jndiObjectFB.setResourceRef(true);
        jndiObjectFB.setProxyInterface(DataSource.class);
        log.info("Getting datasource with prod profile with jndi");
        return jndiObjectFB;
    }

    /*
        Persistent
     */
    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
        LocalSessionFactoryBean sfb = new LocalSessionFactoryBean();
        sfb.setDataSource(dataSource);
        sfb.setPackagesToScan(new String[] { "com.wms.persistents.model" });
        Properties props = new Properties();
        props.setProperty("dialect", "org.hibernate.dialect.Oracle10gDialect");
        sfb.setHibernateProperties(props);
        return sfb;
    }

//    @Bean
//    public PlatformTransactionManager transactionManager() {
//        return new HibernateTransactionManager(sessionFactory(dataSource()).getObject());
//    }
//    @Bean
//    public HibernateTemplate hibernateTemplate() {
//        return new HibernateTemplate(sessionFactory(dataSource()).getObject());
//    }
}
