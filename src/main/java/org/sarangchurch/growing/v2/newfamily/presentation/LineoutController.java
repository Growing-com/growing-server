package org.sarangchurch.growing.v2.newfamily.presentation;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.newfamily.application.lineout.LineoutService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LineoutController {

    private final LineoutService lineoutService;

    @PostMapping("/api/v2/new-families/{newFamilyId}/line-out")
    public void lineout(@PathVariable Long newFamilyId) {
        lineoutService.lineout(newFamilyId);
    }
}
