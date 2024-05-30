package org.sarangchurch.growing.core.enumconfig;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class EnumController {

    private final EnumMapper enumMapper;

    @GetMapping("/api/common/enums")
    public Map<String, List<EnumValue>> getEnumValue(@RequestParam(required = false) String name) {
        if (name == null) {
            return enumMapper.getAll();
        }
        return enumMapper.get(name);
    }
}
