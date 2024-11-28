package org.sarangchurch.growing.v1.feat.newfamily.infra.data.newfamily;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamily;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamilyRepository;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamilyStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class NewFamilyFinder {
    private final NewFamilyRepository newFamilyRepository;

    public NewFamily findByIdOrThrow(Long id) {
        return newFamilyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("새가족을 찾을 수 없습니다"));
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

        return newFamily.statusNotEquals(NewFamilyStatus.PROMOTED);
    }

    public boolean containsNewFamilyByUserIds(List<Long> userIds) {
        return userIds.stream()
                .anyMatch(this::isNewFamilyByUserId);
    }

    public boolean existsByNewFamilyGroupId(Long newFamilyGroupId) {
        return newFamilyRepository.existsByNewFamilyGroupId(newFamilyGroupId);
    }

    public boolean isNewFamilyById(Long id) {
        Optional<NewFamily> newFamilyOptional = newFamilyRepository.findById(id);

        if (newFamilyOptional.isEmpty()) {
            return false;
        }

        NewFamily newFamily = newFamilyOptional.get();

        return newFamily.statusNotEquals(NewFamilyStatus.PROMOTED);
    }

    public boolean existsBySmallGroupId(Long smallGroupId) {
        return newFamilyRepository.existsBySmallGroupId(smallGroupId);
    }
}
