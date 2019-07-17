package com.example.demo;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class Example5Route extends RouteBuilder {
    @Override
    public void configure() {
        from("file:/tmp/inbox?noop=true").routeId("example5route").autoStartup(false)
                .log("Received file: ${header.CamelFileName}")
                .to("amqp:incoming.q");

        from("amqp:incoming.q")
                .log("Received file: ${header.CamelFileName}")
                .choice()
                    .when(header("CamelFileName").endsWith("pdf"))
                        .to("amqp:document.q")
                    .when(header("CamelFileName").regex("^.*(png|jpg)$"))
                        .to("amqp:image.q")
                    .when(header("CamelFileName").endsWith("txt"))
                        .to("amqp:text.q")
                    .otherwise()
                        .to("amqp:unsupported.q")
                .end();

        from("amqp:document.q")
                .log("Received a new document: ${header.CamelFileName}")
                .to("mock:document");

        from("amqp:image.q")
                .log("Received a new image: ${header.CamelFileName}")
                .to("mock:image");

        from("amqp:text.q")
                .log("Received text: ${header.CamelFileName}")
                .to("mock:text");

        from("amqp:unsupported.q")
                .log("Unsupported document: ${header.CamelFileName}")
                .to("mock:unsupported");

    }
}
