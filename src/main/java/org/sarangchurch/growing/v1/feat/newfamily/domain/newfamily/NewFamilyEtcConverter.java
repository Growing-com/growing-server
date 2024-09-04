package org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class NewFamilyEtcConverter implements AttributeConverter<NewFamilyEtc, String> {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(NewFamilyEtc attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting to JSON", e);
        }
    }

    @Override
    public NewFamilyEtc convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, NewFamilyEtc.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error reading JSON", e);
        }
    }
}
