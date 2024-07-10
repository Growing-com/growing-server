package org.sarangchurch.growing.v2.feat.newfamily.presentation;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.feat.newfamily.application.batchlneup.BatchLineupRequest;
import org.sarangchurch.growing.v2.feat.newfamily.application.batchlneup.BatchLineupService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class BatchLineupController {
    private final BatchLineupService batchLineupService;

    @PostMapping("/api/v2/new-families/batch-line-up")
    public void batchLineup(@RequestBody @Valid BatchLineupRequest request) {
        batchLineupService.lineup(request);
    }
}
