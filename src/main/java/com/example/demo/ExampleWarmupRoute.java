package com.example.demo;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ExampleWarmupRoute extends RouteBuilder {
    @Override
    public void configure() {
        from("direct:warmup").routeId("warmup")
                .choice()
                    .when(body().contains("hello")).to("direct:hello")
                    .otherwise().to("direct:bye")
                .end();

        from("direct:hello").routeId("hello")
                .setBody(constant("Hello World!"))
                .to("mock:hello");

        from("direct:bye").routeId("bye")
                .setBody(constant("Bye!"))
                .to("mock:bye");
    }
}
