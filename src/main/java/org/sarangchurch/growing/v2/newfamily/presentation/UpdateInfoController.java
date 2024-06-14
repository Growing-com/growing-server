package org.sarangchurch.growing.v2.newfamily.presentation;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.newfamily.application.updateinfo.UpdateInfoRequest;
import org.sarangchurch.growing.v2.newfamily.application.updateinfo.UpdateInfoService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UpdateInfoController {

    private final UpdateInfoService updateInfoService;

    @PostMapping("/api/v2/new-families/{newFamilyId}/update-info")
    public void updateInfo(@PathVariable Long newFamilyId, @RequestBody @Valid UpdateInfoRequest request) {
        updateInfoService.update(newFamilyId, request);
    }
}
