package com.example.demo;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class Example17Route extends RouteBuilder {

    @Override
    public void configure() {
        from("imap://claus@localhost?password=secret").to("mock:example17route");
    }
}
