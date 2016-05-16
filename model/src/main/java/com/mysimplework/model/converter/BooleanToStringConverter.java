package com.mysimplework.model.converter;

import javax.persistence.AttributeConverter;

/**
 * Created by dzhao on 22/09/2015.
 */
public class BooleanToStringConverter implements AttributeConverter<Boolean, String> {

    public String convertToDatabaseColumn(Boolean value) {
        return (value != null && value) ? "Y" : "N";
    }

    public Boolean convertToEntityAttribute(String value) {
        return "Y".equals(value);
    }
}
