package com.example.demo;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@RunWith(CamelSpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class Example15RouteTest {
    @Autowired
    ProducerTemplate producerTemplate;

    @Autowired
    CamelContext camelContext;

    MockEndpoint getMockEndpoint(String uri) {
        return camelContext.getEndpoint(uri, MockEndpoint.class);
    }

    @Test
    public void mixitRoute_deadletter() throws Exception {
        // given:
        camelContext.getRouteDefinition("task15").adviceWith(camelContext, new AdviceWithRouteBuilder() {
                @Override
                public void configure() throws Exception {
                    mockEndpoints();
                }
            }
        );

        // then:
        getMockEndpoint("mock://direct:task15").expectedBodiesReceived("1", "2", "3", "4", "fizz");
        getMockEndpoint("mock:task15:result").expectedBodiesReceived(1, 2, 3, 4);
        getMockEndpoint("mock:task15:dlq").expectedBodiesReceived("fizz");

        // when:
        producerTemplate.sendBody("direct:task15", "1");
        producerTemplate.sendBody("direct:task15", "2");
        producerTemplate.sendBody("direct:task15", "3");
        producerTemplate.sendBody("direct:task15", "4");
        producerTemplate.sendBody("direct:task15", "fizz");
        MockEndpoint.assertIsSatisfied(camelContext);
    }

}
