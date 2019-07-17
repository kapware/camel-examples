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
public class Example7RouteTest {
    @Autowired
    ProducerTemplate producerTemplate;

    @Autowired
    CamelContext camelContext;

    MockEndpoint getMockEndpoint(String uri) {
        return camelContext.getEndpoint(uri, MockEndpoint.class);
    }

    @Test
    public void mixitRoute_toUpperCase() throws Exception {
        // given:
        String body = "hello";
        camelContext.getRouteDefinition("example7").adviceWith(camelContext, new AdviceWithRouteBuilder() {
                @Override
                public void configure() throws Exception {
                    mockEndpoints();
                }
            }
        );

        // then:
        getMockEndpoint("mock://direct:example7").expectedBodiesReceived("hello");
        getMockEndpoint("mock:result1").expectedBodiesReceived("HELLO");
        getMockEndpoint("mock:result2").expectedBodiesReceived("HELLO");
        getMockEndpoint("mock:result3").expectedBodiesReceived("HELLO");
        getMockEndpoint("mock:result4").expectedBodiesReceived("HELLO");
        getMockEndpoint("mock:result5").expectedBodiesReceived("HELLO");

        // when:
        producerTemplate.sendBody("direct:example7", body);
        MockEndpoint.assertIsSatisfied(camelContext);
    }

}
