package com.example.demo;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class Example15Route extends RouteBuilder {
    @Override
    public void configure() {
        errorHandler(deadLetterChannel("mock:task15:dlq"));

        from("direct:task15").routeId("task15")
                .transform().message(message -> Integer.parseInt(message.getBody(String.class)))
                    .to("mock:task15:result")
                    ;
    }
}
