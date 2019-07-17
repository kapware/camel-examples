package com.example.demo;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class Example11Route extends RouteBuilder {
    @Override
    public void configure() {
        from("direct:example11").routeId("example11")
                .split(body().tokenize(" "))
                    .to("mock:example11route:result")
                    ;
    }
}
