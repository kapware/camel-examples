package com.example.demo;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class Example9Route extends RouteBuilder {

    @Override
    public void configure() {
        from("file:/tmp/inbox?noop=true").routeId("example9route").autoStartup(false)
                .log("Received file: ${header.CamelFileName}")
                .to("amqp:incoming.q");

        from("amqp:incoming.q")
                .wireTap("amqp:audit")
                .log("Received file: ${header.CamelFileName}")
                .choice()
                    .when(header("CamelFileName").endsWith("xml"))
                        .to("amqp:xml.q")
                    .when(header("CamelFileName").endsWith("json"))
                        .to("amqp:json.q")
                    .otherwise()
                        .to("amqp:unsupported.q")
                .end();

        from("amqp:xml.q").bean(XMLHandlerBean.class);

        from("amqp:players")
                .log("New player: ${header.CamelFileName}");

        from("amqp:registered")
                .log("New registered player: ${header.CamelFileName}");

        from("amqp:unregistered")
                .log("New unregistered player: ${header.CamelFileName}");

        from("amqp:json.q")
                .filter().jsonpath("$.player", true)
                .log("Json player: ${header.CamelFileName}")
                .to("mock:unregisteredJson");

        from("amqp:unsupported.q")
                .log("Unsupported document: ${header.CamelFileName}")
                .to("mock:unsupported");

        from("amqp:audit")
                .log("Audit trail: ${header.CamelFileName}")
                .to("mock:audit");

    }
}
