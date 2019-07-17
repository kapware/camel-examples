package com.example.demo;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class Example13Route extends RouteBuilder {

    @Override
    public void configure() {
        from("direct:example13a").routeId("example13a")
                .transform().message(message -> {
                    message.setHeader("meta:translator","Transformed with message");
                    Person person = message.getBody(Person.class);
                    return Player.builder()
                            .alias(person.getFirstName().substring(0, 3).toLowerCase())
                            .email(person.getEmail())
                            .build();
                })
                .to("mock:example13a:result")
        ;

        from("direct:example13b").routeId("example13b")
                .transform().exchange(ex -> {
                    ex.getMessage().setHeader("meta:translator","Transformed with message");
                    Person person = ex.getMessage().getBody(Person.class);
                    return Player.builder()
                            .alias(person.getFirstName().substring(0, 3).toLowerCase())
                            .email(person.getEmail())
                            .build();
                  })
                .to("mock:example13b:result")
        ;

        from("direct:example13c").routeId("example13c")
                .process(ex -> {
                    ex.getMessage().setHeader("meta:translator", "Transformed with message");
                    Person person = ex.getMessage().getBody(Person.class);
                    ex.getIn().setBody(Player.builder()
                            .alias(person.getFirstName().substring(0, 3).toLowerCase())
                            .email(person.getEmail())
                            .build());
                })
                .to("mock:example13c:result");
    }
}
