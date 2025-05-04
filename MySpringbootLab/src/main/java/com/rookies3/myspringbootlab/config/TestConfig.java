package com.rookies3.myspringbootlab.config;

import org.springframework.context.annotation.*;

@Configuration
@Profile("test")
public class TestConfig {
    @Bean
    public MyEnvironment myEnvironment() {
        MyEnvironment env = new MyEnvironment();
        env.setMode("테스트환경");
        return env;
    }
}