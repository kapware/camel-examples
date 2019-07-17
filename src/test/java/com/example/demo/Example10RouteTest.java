package com.example.demo;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.test.spring.CamelSpringRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(CamelSpringRunner.class)
public class Example10RouteTest {
    @Autowired
    CamelContext camelContext;

    @Autowired
    ProducerTemplate producerTemplate;

    @Test
    public void typeConverter()  {
        byte[] message = "!@^#E$#:John|Doe|21|__This is an unknown person__:!@^#E$#".getBytes();
        PersonalDetails result = producerTemplate.requestBody("direct:example10Route", message, PersonalDetails.class);

        assertThat(result).isNotNull();
        assertThat(result.getFirstName()).isEqualTo("John");
        assertThat(result.getLastName()).isEqualTo("Doe");
        assertThat(result.getAge()).isEqualTo(21);
        assertThat(result.getDescription()).isEqualTo("This is an unknown person");
    }

}
