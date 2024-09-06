package org.sarangchurch.growing.v1.feat.newfamily.presentation;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.application.linein.V1LineInService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class V1LineInController {
    private final V1LineInService lineInService;

    @PostMapping("/api/v1/line-out-new-families/{lineOutNewFamilyId}/line-in")
    public void lineIn(@PathVariable Long lineOutNewFamilyId) {
        lineInService.lineIn(lineOutNewFamilyId);
    }
}
