package com.example.demo;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(CamelSpringRunner.class)
public class Example17RouteTest {
    @Autowired
    CamelContext camelContext;

    @Autowired
    ProducerTemplate template;

    MockEndpoint getMockEndpoint(String uri) {
        return camelContext.getEndpoint(uri, MockEndpoint.class);
    }

    @Test
    public void sendAndReceiveMail() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:example17route");
        mock.expectedBodiesReceived("Yes, Camel rocks!");

        template.sendBody("smtp://jon@localhost?password=secret&to=claus@localhost", "Yes, Camel rocks!");

        mock.assertIsSatisfied();
    }
}
