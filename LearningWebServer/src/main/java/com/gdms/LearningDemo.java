package com.gdms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * Main starting class for the Deep Learning Webserver Demo Application
 *
 */
@ComponentScan({ "com.gdms" })
@SpringBootApplication
public class LearningDemo extends SpringBootServletInitializer {
    static final Logger LOG = LoggerFactory.getLogger(LearningDemo.class);

    /**
     * Main method for Deep Learning Webserver Demo
     *
     * @param args
     *            - Application inputs (none used)
     */
    public static void main(String[] args) {
        LOG.info("============================== starting up application");
        SpringApplication.run(LearningDemo.class, args);
    }

    /**
     * Configures Spirit application
     *
     * @param application
     *            - The application
     * @return The spring application builder
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(LearningDemo.class);
    }

}
