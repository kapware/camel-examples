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
public class ExampleWarmupTest {
    @Autowired
    ProducerTemplate producerTemplate;

    @Autowired
    CamelContext camelContext;

    MockEndpoint getMockEndpoint(String uri) {
        return camelContext.getEndpoint(uri, MockEndpoint.class);
    }

    @Test
    public void warmup_hello() throws Exception {
        // given:
        String body = "hello";
        camelContext.getRouteDefinition("warmup").adviceWith(camelContext, new AdviceWithRouteBuilder() {
                @Override
                public void configure() throws Exception {
                    mockEndpoints();
                }
            }
        );

        // then:
        getMockEndpoint("mock://direct:warmup").expectedBodiesReceived("hello");
        getMockEndpoint("mock:hello").expectedBodiesReceived("Hello World!");

        // when:
        producerTemplate.sendBody("direct:warmup", body);
        MockEndpoint.assertIsSatisfied(camelContext);
    }

    @Test
    public void warmup_bye() throws Exception {
        // given:
        String body = "this is a goodbye";
        camelContext.getRouteDefinition("warmup").adviceWith(camelContext, new AdviceWithRouteBuilder() {
                    @Override
                    public void configure() throws Exception {
                        mockEndpoints();
                    }
                }
        );

        // then:
        getMockEndpoint("mock://direct:warmup").expectedBodiesReceived("this is a goodbye");
        getMockEndpoint("mock:bye").expectedBodiesReceived("Bye!");

        // when:
        producerTemplate.sendBody("direct:warmup", body);
        MockEndpoint.assertIsSatisfied(camelContext);
    }
}
