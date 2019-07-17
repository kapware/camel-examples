package com.example.demo;

import org.apache.camel.Exchange;
import org.apache.camel.TypeConversionException;
import org.apache.camel.TypeConverter;
import org.apache.camel.support.TypeConverterSupport;

public class PersonalDetailsConverter extends TypeConverterSupport {

    @Override
    @SuppressWarnings("unchecked")
    public <T> T convertTo(Class<T> type, Exchange exchange, Object value) throws TypeConversionException {
        if (!type.isAssignableFrom(PersonalDetails.class)) {
            throw new TypeConversionException(value, type, new Exception("This converter supports only PersonalDetails type"));
        }
        if (!(value instanceof byte[])) {
            throw new TypeConversionException(value, type, new Exception("This converter supports only byte[] params"));
        }
        byte[] data = (byte[]) value;
        TypeConverter converter = exchange.getContext().getTypeConverter();
        String s = converter.convertTo(String.class, data);

        s = s.replaceAll("!@\\^#E\\$#", "");
        System.out.println(s);
        s = s.replaceAll(":", "");
        System.out.println(s);

        String[] details = s.split("\\|");

        return (T) PersonalDetails.builder()
                .firstName(details[0])
                .lastName(details[1])
                .age(Integer.parseInt(details[2]))
                .description(details[3].replaceAll("__", ""))
                .build();
    }

}
