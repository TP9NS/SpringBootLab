package com.rookies3.myspringbootlab.runner;

import com.rookies3.myspringbootlab.config.MyEnvironment;
import com.rookies3.myspringbootlab.property.MyPropProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyPropRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(MyPropRunner.class);
    private final MyPropProperties prop;
    private final MyEnvironment env;

    public MyPropRunner(MyPropProperties prop, MyEnvironment env) {
        this.prop = prop;
        this.env = env;
    }

    @Override
    public void run(String... args) {
        logger.info("username: {}", prop.getUsername());
        logger.debug("port: {}", prop.getPort());
        logger.info("현재 실행 환경: {}", env.getMode());
    }
}