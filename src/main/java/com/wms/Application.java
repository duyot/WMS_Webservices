package com.wms;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by duyot on 1/16/2017.
 */
@SpringBootApplication( exclude = { DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@Configuration
@EnableAutoConfiguration
@ComponentScan(value = "com.wms")
@EnableTransactionManagement
public class Application   extends SpringBootServletInitializer {
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        // Customize the application or call application.sources(...) to add sources
//        // Since our example is itself a @Configuration class (via @SpringBootApplication)
//        // we actually don't need to override this method.
//        return application;
//    }

//    @Bean
//    public CacheManager cacheManager() {
//        return new ConcurrentMapCacheManager("menus");
//    }


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
