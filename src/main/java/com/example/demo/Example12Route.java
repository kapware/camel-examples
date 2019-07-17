package com.example.demo;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class Example12Route extends RouteBuilder {

    @Override
    public void configure() {
        from("direct:example12route").routeId("example12route")
                .unmarshal().json(JsonLibrary.Jackson, Player.class)
                // translation/processing here
                .marshal().jacksonxml()
                .to("mock:example12route:result")
        ;
    }
}
