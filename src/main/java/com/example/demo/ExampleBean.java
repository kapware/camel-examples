package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class ExampleBean {
    Logger logger = LoggerFactory.getLogger(ExampleBean.class);

    public String say(String something) {
        String result = "This is my only line, and " + something;
        // Some more logic
        logger.info(result);
        return result;
    }
}
