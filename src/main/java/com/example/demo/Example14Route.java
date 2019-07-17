package com.example.demo;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Example14Route extends RouteBuilder {
    @Autowired
    ExampleBean exampleBean;

    @Override
    public void configure() {
        from("direct:example14a")
                .bean(ExampleBean.class, "say");


        from("direct:example14b")
                .process(exchange -> {
                    String name = exchange.getIn().getBody(String.class);

                    ExampleBean hello = new ExampleBean();
                    String answer = hello.say(name);

                    exchange.getOut().setBody(answer);
                });

        from("direct:example14c")
                .process(exchange -> {
                    String name = exchange.getIn().getBody(String.class);

                    String answer = exampleBean.say(name);

                    exchange.getOut().setBody(answer);
                });

        // This will work only with one public method, otherwise exception
        from("direct:example14d")
                .bean(ExampleBean.class);
    }
}
