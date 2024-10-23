package org.sarangchurch.growing.v1.feat.term.presentation.pastor;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.application.pastor.deletepastor.DeletePastorService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeletePastorController {
    private final DeletePastorService deletePastorService;

    @PostMapping("/api/v1/pastors/{pastorId}/delete")
    public void deletePastor(@PathVariable Long pastorId) {
        deletePastorService.delete(pastorId);
    }
}
