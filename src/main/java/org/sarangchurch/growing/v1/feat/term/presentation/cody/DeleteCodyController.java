package org.sarangchurch.growing.v1.feat.term.presentation.cody;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.application.cody.deletecody.DeleteCodyService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeleteCodyController {
    private final DeleteCodyService deleteCodyService;

    @PostMapping("/api/v1/codies/{codyId}/delete")
    public void deleteCody(@PathVariable Long codyId) {
        deleteCodyService.delete(codyId);
    }
}
