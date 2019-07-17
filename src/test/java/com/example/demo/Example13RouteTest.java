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
public class Example13RouteTest {
    @Autowired
    ProducerTemplate producerTemplate;

    @Test
    public void messageTranslation() {
        Person person = Person.builder().email("john.doe@example.com").firstName("John").lastName("Doe").build();
        Player expectedPlayer = Player.builder().alias("joh").email("john.doe@example.com").build();

        Player replyA = producerTemplate.requestBody("direct:example13a", person, Player.class);
        assertThat(replyA).isEqualTo(expectedPlayer);

        Player replyB = producerTemplate.requestBody("direct:example13b", person, Player.class);
        assertThat(replyB).isEqualTo(expectedPlayer);

        Player replyC = producerTemplate.requestBody("direct:example13c", person, Player.class);
        assertThat(replyC).isEqualTo(expectedPlayer);
    }

}
