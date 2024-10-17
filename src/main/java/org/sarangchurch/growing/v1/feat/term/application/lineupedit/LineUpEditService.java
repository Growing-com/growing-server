package org.sarangchurch.growing.v1.feat.term.application.lineupedit;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.infra.component.LineUpEditor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LineUpEditService {
    private final LineUpEditor lineUpEditor;

    public void edit(Long smallGroupMemberId, LineUpEditRequest request) {
        lineUpEditor.edit(smallGroupMemberId, request.getTargetSmallGroupId());
    }
}
