package com.example.demo;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class Example7Route extends RouteBuilder {
    @Override
    public void configure() {
        from("direct:example7").routeId("example7")
                .transform(simple("${body.toUpperCase()}"))
                .multicast()
                    .to("mock:result1")
                    .to("mock:result2")
                    .to("mock:result3")
                    .to("mock:result4")
                    .to("mock:result5")
                    ;
    }
}
