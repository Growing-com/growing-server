package org.sarangchurch.growing.v1.feat.term.presentation;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.application.lineupedit.LineUpEditRequest;
import org.sarangchurch.growing.v1.feat.term.application.lineupedit.LineUpEditService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class LineUpEditController {
    private final LineUpEditService lineUpEditService;

    @PostMapping("/api/v1/line-up-edit")
    public void lineUpEdit(@RequestBody @Valid LineUpEditRequest request) {
        lineUpEditService.edit(request);
    }
}
