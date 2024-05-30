package org.sarangchurch.growing.core.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.sarangchurch.growing.core.types.Week;

import java.io.IOException;

public class WeekSerializer extends StdSerializer<Week> {

    public WeekSerializer(Class<Week> aClass) {
        super(aClass);
    }

    @Override
    public void serialize(Week value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(value.toString());
    }
}
