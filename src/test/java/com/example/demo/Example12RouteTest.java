package com.example.demo;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringRunner;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@RunWith(CamelSpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class Example12RouteTest {
    @Autowired
    ProducerTemplate producerTemplate;

    @Autowired
    CamelContext camelContext;

    MockEndpoint getMockEndpoint(String uri) {
        return camelContext.getEndpoint(uri, MockEndpoint.class);
    }

    @Test
    public void messageTranslator() throws Exception {
        // given:
        String playerJson = IOUtils.toString(this.getClass().getResource("/task12input.json"), "UTF-8");
        camelContext.getRouteDefinition("example12route").adviceWith(camelContext, new AdviceWithRouteBuilder() {
                @Override
                public void configure() throws Exception {
                    mockEndpoints();
                }
            }
        );

        // then:
        String expectedResult = IOUtils.toString(this.getClass().getResource("/task12expected.xml"), "UTF-8");
        getMockEndpoint("mock://direct:example12route").expectedBodiesReceived(playerJson);
        getMockEndpoint("mock:example12route:result").expectedBodiesReceived(expectedResult);

        // when:
        producerTemplate.sendBody("direct:example12route", playerJson);
        MockEndpoint.assertIsSatisfied(camelContext);
    }

}
