package com.example.demo;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class Example4Route extends RouteBuilder {
    @Override
    public void configure() {
        from("file:/tmp/inbox?noop=true").routeId("example4route").autoStartup(false).to("file:/tmp/outbox");
    }
}
