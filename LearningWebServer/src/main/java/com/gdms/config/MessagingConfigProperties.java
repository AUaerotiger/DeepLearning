package com.gdms.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * This class aids the user in manipulating application.properties messaging properties
 *
 */
@Configuration
@ConfigurationProperties("messaging")
public class MessagingConfigProperties {

    /**
     * The URL used for local connections. All listeners connect to this URL. Senders will connect
     * to this URL if local-host = true
     */
    private String localUrl;

    /**
     * Accessor for the localUrl
     *
     * @return the localUrl
     */
    public String getLocalUrl() {
        return this.localUrl;
    }

    /**
     * Mutator for the localUrl
     *
     * @param localUrl
     *            - the localUrl to set
     */
    public void setLocalUrl(String localUrl) {
        this.localUrl = localUrl;
    }
}
