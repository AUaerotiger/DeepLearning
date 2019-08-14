package com.gdms.config;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Provides configuration for the embedded Tomcat server and the AJP Tomcat server.
 * This configuration forwards all data to the apache proxy web server.
 *
 */
@Configuration
public class ServerConfiguration {

    @Value("${ajp.port}")
    int ajpPort;

    @Value("${ajp.enabled}")
    boolean ajpEnabled;

    /**
     * Sets up the Tomcat container.
     *
     * @return the Tomcat embedded container factory set up to use AJP if set in application
     *         properties.
     */
    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();

        if (this.ajpEnabled) {
            Connector ajpConnector = new Connector("AJP/1.3");
            ajpConnector.setPort(this.ajpPort);
            ajpConnector.setSecure(false);
            ajpConnector.setScheme("http");
            ajpConnector.setAllowTrace(false);
            tomcat.addAdditionalTomcatConnectors(ajpConnector);
        }

        return tomcat;
    }

}
