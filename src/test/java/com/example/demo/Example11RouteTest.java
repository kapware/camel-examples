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
public class Example11RouteTest {
    @Autowired
    ProducerTemplate producerTemplate;

    @Autowired
    CamelContext camelContext;

    MockEndpoint getMockEndpoint(String uri) {
        return camelContext.getEndpoint(uri, MockEndpoint.class);
    }

    @Test
    public void mixitRoute_sentenceSplit() throws Exception {
        // given:
        String loremIpsum = "Lorem ipsum dolor sit amet,";
        camelContext.getRouteDefinition("example11Route").adviceWith(camelContext, new AdviceWithRouteBuilder() {
                @Override
                public void configure() throws Exception {
                    mockEndpoints();
                }
            }
        );

        // then:
        getMockEndpoint("mock://direct:example11Route").expectedBodiesReceived(loremIpsum);
        getMockEndpoint("mock:example11Route:result").expectedBodiesReceived(
                "Lorem",
                "ipsum",
                "dolor",
                "sit",
                "amet,"
        );

        // when:
        producerTemplate.sendBody("direct:example11Route", loremIpsum);
        MockEndpoint.assertIsSatisfied(camelContext);
    }

}
