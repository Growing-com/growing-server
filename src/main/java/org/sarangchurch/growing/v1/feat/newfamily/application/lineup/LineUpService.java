package org.sarangchurch.growing.v1.feat.newfamily.application.lineup;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.infra.component.NewFamilyLineUpManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LineUpService {
    private final NewFamilyLineUpManager lineUpManager;

    public void lineUp(LineUpRequest request) {
        List<Long> newFamilyIds = request.getContent()
                .stream()
                .map(LineUpRequest.V1LineUpRequestItem::getNewFamilyId)
                .collect(Collectors.toList());

        List<Long> smallGroupIds = request.getContent()
                .stream()
                .map(LineUpRequest.V1LineUpRequestItem::getSmallGroupId)
                .collect(Collectors.toList());

        lineUpManager.lineUp(newFamilyIds, smallGroupIds);
    }
}
