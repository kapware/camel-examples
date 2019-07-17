package com.example.demo;

import org.apache.camel.CamelContext;
import org.apache.camel.test.spring.CamelSpringRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(CamelSpringRunner.class)
public class Example5RouteTest {
    @Autowired
    CamelContext context;

    @Test
    public void contentBasedRouter() throws Exception {
        context.startRoute("example5route");
        Thread.sleep(10000);

    }
}
