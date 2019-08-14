package com.gdms.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * This class aids the user in manipulating application.properties ajp properties
 *
 */
@Configuration
@ConfigurationProperties("ajp")
public class AjpConfigProperties {

    /**
     * This is the port to connection with Apache HTTP
     */
    private int port;

    /**
     * Enables AJP for Apache port forwarding
     */
    private boolean enabled;

    /**
     * Accessor for the port
     *
     * @return the port
     */
    public int getPort() {
        return this.port;
    }

    /**
     * Mutator for the port
     *
     * @param port
     *            - the port to set
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Accessor for the ajp enabled status
     *
     * @return the enabled status
     */
    public boolean isEnabled() {
        return this.enabled;
    }

    /**
     * Mutator for the enabled status
     *
     * @param enabled
     *            - the enabled status to set
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
