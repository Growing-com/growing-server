package org.sarangchurch.growing.v1.feat.term.application;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.infra.component.LineUpEditor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LineUpEditService {
    private final LineUpEditor lineUpEditor;

    public void edit(Long smallGroupMemberId, Long targetSmallGroupId) {
        lineUpEditor.edit(smallGroupMemberId, targetSmallGroupId);
    }
}
