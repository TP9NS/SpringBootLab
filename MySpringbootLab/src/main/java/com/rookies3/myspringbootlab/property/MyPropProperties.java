package com.rookies3.myspringbootlab.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("myprop")
@Getter
@Setter
public class MyPropProperties {
    private String username;
    private int port;
}