package com.wms.config;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by duyot on 8/23/2016.
 */
@Configuration
public class BeanConfig {

    Logger log = LoggerFactory.getLogger(BeanConfig.class);

    //init datasource fron jndi
    @Bean
    @Primary
    public DataSource primaryDataSource() {
        log.info("Getting datasource with prod profile with jndi: java:comp/env/jdbc/ApplicationPool");
        JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
        DataSource dataSource = dataSourceLookup.getDataSource("java:comp/env/jdbc/ApplicationPool");
        log.info("Getting datasource: " + dataSource.toString());
        return dataSource;
    }

    /*
        Persistent
     */
    @Bean
    public SessionFactory sessionFactory(DataSource dataSource) throws Exception {
        LocalSessionFactoryBean sfb = new LocalSessionFactoryBean();
        sfb.setDataSource(dataSource);
        sfb.setPackagesToScan(new String[]{"com.wms.persistents.model"});
        Properties props = new Properties();
        props.setProperty("dialect", "org.hibernate.dialect.Oracle10gDialect");
        props.put("logging.level.org.hibernate.SQL", "DEBUG");
        props.put("logging.level.org.hibernate.type", "TRACE");
        props.put("hibernate.show_sql", true);
        sfb.setHibernateProperties(props);
        sfb.afterPropertiesSet();
        SessionFactory sf = sfb.getObject();
        return sf;
    }
    @Autowired
    @Bean(name = "transactionManager")
    public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
        return transactionManager;
    }

}
