package org.sarangchurch.growing.core.enumconfig;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EnumMapper {
    private final Map<String, List<EnumValue>> factory = new HashMap<>();

    public void put(String key, Class<? extends EnumModel> e){
        factory.put(key, toEnumValues(e));
    }

    private List<EnumValue> toEnumValues(Class<? extends EnumModel> e){
        return Arrays
                .stream(e.getEnumConstants())
                .map(EnumValue::new)
                .collect(Collectors.toList());
    }

    public Map<String, List<EnumValue>> getAll(){
        return factory;
    }

    public Map<String, List<EnumValue>> get(String key){
        return Map.of(key, factory.get(key));
    }
}
