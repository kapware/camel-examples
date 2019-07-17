package com.example.demo;

import org.apache.camel.CamelContext;
import org.apache.camel.test.spring.CamelSpringRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(CamelSpringRunner.class)
public class Example9RouteTest {
    @Autowired
    CamelContext context;

    @Test
    public void contentBasedRouter_filter() throws Exception {
        context.startRoute("example9route");
        Thread.sleep(20000);

    }
}
