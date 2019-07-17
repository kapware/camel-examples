package com.example.demo;

import org.apache.camel.RecipientList;
import org.apache.camel.language.XPath;

public class XMLHandlerBean {

    @RecipientList
    public String[] route(@XPath("/player/@type") String playerType) {
        if ("registered".equals(playerType)) {
            return new String[] {"amqp:registered", "amqp:players"};
        } else {
            return new String[] {"amqp:unregistered", "amqp:players"};
        }
    }
}
