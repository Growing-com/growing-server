package org.sarangchurch.growing.v2.newfamily.presentation;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.newfamily.application.lineup.LineupRequest;
import org.sarangchurch.growing.v2.newfamily.application.lineup.LineupService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class LineupController {

    private final LineupService lineupService;

    @PostMapping("/api/v2/new-families/{newFamilyId}/line-up")
    public void lineup(@PathVariable Long newFamilyId, @RequestBody @Valid LineupRequest request) {
        lineupService.lineup(newFamilyId, request);
    }

}
