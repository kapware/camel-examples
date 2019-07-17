package com.example.demo;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class Example6Route extends RouteBuilder {
    @Override
    public void configure() {
        from("file:/tmp/inbox?noop=true").routeId("example6route").autoStartup(false)
                .log("Received file: ${header.CamelFileName}")
                .to("amqp:incoming.q");

        from("amqp:incoming.q")
                .log("Received file: ${header.CamelFileName}")
                .choice()
                    .when(header("CamelFileName").endsWith("xml"))
                        .to("amqp:xml.q")
                    .when(header("CamelFileName").endsWith("json"))
                        .to("amqp:json.q")
                    .otherwise()
                        .to("amqp:unsupported.q")
                .end();

        from("amqp:xml.q")
                .filter().xpath("/player")
                .log("Xml player: ${header.CamelFileName}")
                .to("mock:unregisteredXml");

        from("amqp:json.q")
                .filter().jsonpath("$.player", true)
                .log("Json player: ${header.CamelFileName}")
                .to("mock:unregisteredJson");

        from("amqp:unsupported.q")
                .log("Unsupported document: ${header.CamelFileName}")
                .to("mock:unsupported");

    }
}
