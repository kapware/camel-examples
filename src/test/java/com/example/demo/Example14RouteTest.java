package com.example.demo;

import org.apache.camel.ProducerTemplate;
import org.apache.camel.test.spring.CamelSpringRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(CamelSpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class Example14RouteTest {
    @Autowired
    ProducerTemplate producerTemplate;

    @Test
    public void usingBeans() {
        String replyA = producerTemplate.requestBody("direct:example14a", "Hi!", String.class);
        assertThat(replyA).isEqualTo("This is my only line, and Hi!");

        String replyB = producerTemplate.requestBody("direct:example14b", "Ho!", String.class);
        assertThat(replyB).isEqualTo("This is my only line, and Ho!");

        String replyC = producerTemplate.requestBody("direct:example14c", "Hello!", String.class);
        assertThat(replyC).isEqualTo("This is my only line, and Hello!");

        String replyD = producerTemplate.requestBody("direct:example14d", "Woah!", String.class);
        assertThat(replyD).isEqualTo("This is my only line, and Woah!");
    }

}
