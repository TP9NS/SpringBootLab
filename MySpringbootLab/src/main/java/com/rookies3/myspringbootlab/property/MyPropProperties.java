package com.rookies3.myspringbootlab.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "myprop")
public class MyPropProperties {
    private String username;
    private int port;

    // getter/setter
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public int getPort() { return port; }
    public void setPort(int port) { this.port = port; }
}