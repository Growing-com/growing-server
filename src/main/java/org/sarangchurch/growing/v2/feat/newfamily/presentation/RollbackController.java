package org.sarangchurch.growing.v2.feat.newfamily.presentation;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.feat.newfamily.application.rollback.RollbackRequest;
import org.sarangchurch.growing.v2.feat.newfamily.application.rollback.RollbackService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class RollbackController {
    private final RollbackService rollbackService;

    @PostMapping("/api/v2/lined-out-new-families/{linedOutNewFamilyId}/rollback")
    public void rollback(@PathVariable Long linedOutNewFamilyId, @RequestBody @Valid RollbackRequest request) {
        rollbackService.rollback(linedOutNewFamilyId, request);
    }
}
