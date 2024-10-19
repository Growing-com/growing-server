package org.sarangchurch.growing.v1.feat.newfamily.infra.data;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamily;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamilyRepository;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilypromotelog.NewFamilyPromoteLog;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilypromotelog.NewFamilyPromoteLogRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class NewFamilyFinder {
    private final NewFamilyRepository newFamilyRepository;
    private final NewFamilyPromoteLogRepository newFamilyPromoteLogRepository;

    public NewFamily findByIdOrThrow(Long id) {
        return newFamilyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("새가족을 찾을 수 없습니다"));
    }

    public boolean existsByIds(List<Long> ids) {
        List<NewFamily> newFamilies = newFamilyRepository.findByIdIn(ids);

        return newFamilies.size() == ids.size();
    }

    public List<NewFamily> findByIdInOrThrow(List<Long> ids) {
        List<NewFamily> newFamilies = newFamilyRepository.findByIdIn(ids);

        if (ids.size() != newFamilies.size()) {
            throw new IllegalArgumentException("존재하지 않는 새가족이 포함되어 있습니다.");
        }

        return newFamilies;
    }

    public boolean isNewFamilyByUserId(Long userId) {
        Optional<NewFamily> newFamilyOptional = newFamilyRepository.findByUserId(userId);

        if (newFamilyOptional.isEmpty()) {
            return false;
        }

        NewFamily newFamily = newFamilyOptional.get();

        if (newFamily.hasPromoteLog()) {
            NewFamilyPromoteLog newFamilyPromoteLog = newFamilyPromoteLogRepository.findById(newFamily.getNewFamilyPromoteLogId())
                    .orElseThrow(() -> new IllegalStateException("등반 기록이 존재하지 않습니다."));

            boolean isPromoted = newFamilyPromoteLog.isPromoted();

            // 등반이 완료되지 않았으면 새가족
            return !isPromoted;
        }

        // 등반 기록이 없으면 새가족
        return true;
    }

    public boolean containsNewFamilyByUserIds(List<Long> userIds) {
        return userIds.stream()
                .anyMatch(this::isNewFamilyByUserId);
    }
}
