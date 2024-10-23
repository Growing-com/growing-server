package org.sarangchurch.growing.v1.feat.newfamily.application.propagate.temporarylineup;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.infra.component.propagate.NewFamilyTemporaryLineUpManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TemporaryLineUpService {
    private final NewFamilyTemporaryLineUpManager temporaryLineUpManager;

    public void temporaryLineUp(TemporaryLineUpRequest request) {
        List<Long> newFamilyIds = request.getContent()
                .stream()
                .map(TemporaryLineUpRequest.V1TemporaryLineUpRequestItem::getNewFamilyId)
                .collect(Collectors.toList());

        List<List<Long>> smallGroupIds = request.getContent()
                .stream()
                .map(TemporaryLineUpRequest.V1TemporaryLineUpRequestItem::getTemporarySmallGroupIds)
                .collect(Collectors.toList());

        temporaryLineUpManager.temporaryLineUp(newFamilyIds, smallGroupIds);
    }
}
