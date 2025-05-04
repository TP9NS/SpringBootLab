package com.rookies3.myspringbootlab.config;

import org.springframework.context.annotation.*;

@Configuration
@Profile("prod")
public class ProdConfig {
    @Bean
    public MyEnvironment myEnvironment() {
        MyEnvironment env = new MyEnvironment();
        env.setMode("운영환경");
        return env;
    }
}