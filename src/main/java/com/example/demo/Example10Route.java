package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.converter.StaticMethodTypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@RequiredArgsConstructor
public class Example10Route extends RouteBuilder {
    private final CamelContext camelContext;

    @Override
    public void configure() {
        camelContext.getTypeConverterRegistry().addTypeConverter(PersonalDetails.class, byte[].class, new PersonalDetailsConverter());
        from("direct:example10Route").convertBodyTo(PersonalDetails.class);
    }
}
