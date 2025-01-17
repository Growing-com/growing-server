package org.sarangchurch.growing.v1.feat.term.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.sarangchurch.growing.v1.feat.term.infra.data.cody.CodyFinder;
import org.sarangchurch.growing.v1.feat.term.infra.data.pastor.PastorFinder;
import org.sarangchurch.growing.v1.feat.term.infra.data.smallgroup.SmallGroupFinder;
import org.sarangchurch.growing.v1.feat.term.infra.data.smallgroupmember.SmallGroupMemberFinder;
import org.sarangchurch.growing.v1.feat.term.infra.stream.newfamily.newfamily.NewFamilyDownstream;
import org.sarangchurch.growing.v1.feat.term.infra.stream.newfamily.newfamilygroup.NewFamilyGroupDownstream;
import org.sarangchurch.growing.v1.feat.term.infra.stream.newfamily.newfamilygroupmember.NewFamilyGroupMemberDownstream;
import org.sarangchurch.growing.v1.feat.user.domain.user.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AssignValidator {
    private final PastorFinder pastorFinder;
    private final CodyFinder codyFinder;
    private final SmallGroupFinder smallGroupFinder;
    private final SmallGroupMemberFinder smallGroupMemberFinder;
    private final NewFamilyGroupDownstream newFamilyGroupDownstream;
    private final NewFamilyGroupMemberDownstream newFamilyGroupMemberDownstream;
    private final NewFamilyDownstream newFamilyDownstream;

    public void validateAssignable(Term term, User user) {
        // 텀
        if (!term.isActive()) {
            throw new IllegalStateException("비활성 텀입니다.");
        }

        // 유저 활성화
        if (!user.isActive()) {
            throw new IllegalStateException("비활성 유저입니다.");
        }

        // 교역자
        boolean pastorExists = pastorFinder.existsByUserIdAndTermId(user.getId(), term.getId());

        if (pastorExists) {
            throw new IllegalStateException("해당 텀에 이미 교역자로 배정된 유저입니다.");
        }

        // 코디
        boolean codyExists = codyFinder.existsByUserIdAndTermId(user.getId(), term.getId());

        if (codyExists) {
            throw new IllegalStateException("해당 텀에 이미 코디로 배정된 유저입니다.");
        }

        // 일반 순장
        boolean smallGroupExists = smallGroupFinder.existsByLeaderUserIdAndTermId(user.getId(), term.getId());

        if (smallGroupExists) {
            throw new IllegalStateException("해당 텀에 이미 일반 순장으로 배정된 유저입니다.");
        }

        // 일반 순원
        boolean smallGroupMemberExists = smallGroupMemberFinder.existsByUserIdAndTermId(user.getId(), term.getId());

        if (smallGroupMemberExists) {
            throw new IllegalStateException("해당 텀에 이미 일반 순원으로 배정된 유저입니다.");
        }

        // 새가족 순장
        boolean newFamilyGroupLeaderExists = newFamilyGroupDownstream.existsByLeaderUserIdAndTermId(user.getId(), term.getId());

        if (newFamilyGroupLeaderExists) {
            throw new IllegalStateException("해당 텀에 이미 새가족 순장으로 배정된 유저입니다.");
        }

        // 새가족 순원
        boolean newFamilyGroupMemberExists = newFamilyGroupMemberDownstream.existsByUserIdAndTermId(user.getId(), term.getId());

        if (newFamilyGroupMemberExists) {
            throw new IllegalStateException("해당 텀에 이미 새가족 순원으로 배정된 유저입니다.");
        }

        // 새가족
        boolean newFamilyExists = newFamilyDownstream.isNewFamilyByUserId(user.getId());

        if (newFamilyExists) {
            throw new IllegalStateException("새가족을 내부 인원으로 배정할 수 없습니다.");
        }
    }

    public void validateAssignable(Term term, List<User> users) {
        // 텀
        if (!term.isActive()) {
            throw new IllegalStateException("비활성 텀입니다.");
        }

        // 유저 활성화
        List<Long> userIds = users.stream()
                .map(User::getId)
                .collect(Collectors.toList());

        boolean containsInActiveUser = users.stream()
                .anyMatch(it -> !it.isActive());

        if (containsInActiveUser) {
            throw new IllegalStateException("비활성 유저가 포함되어 있습니다.");
        }

        // 교역자
        boolean pastorExists = pastorFinder.existsByUserIdInAndTermId(userIds, term.getId());

        if (pastorExists) {
            throw new IllegalStateException("해당 텀에 이미 교역자로 배정된 유저가 포함되어 있습니다.");
        }

        // 코디
        boolean codyExists = codyFinder.existsByUserIdInAndTermId(userIds, term.getId());

        if (codyExists) {
            throw new IllegalStateException("해당 텀에 이미 코디로 배정된 유저가 포함되어 있습니다.");
        }

        // 일반 순장
        boolean smallGroupExists = smallGroupFinder.existsByLeaderUserIdInAndTermId(userIds, term.getId());

        if (smallGroupExists) {
            throw new IllegalStateException("해당 텀에 이미 일반 순장으로 배정된 유저가 포함되어 있습니다.");
        }

        // 일반 순원
        boolean smallGroupMemberExists = smallGroupMemberFinder.existsByUserIdInAndTermId(userIds, term.getId());

        if (smallGroupMemberExists) {
            throw new IllegalStateException("해당 텀에 이미 일반 순원으로 배정된 유저가 포함되어 있습니다.");
        }

        // 새가족 순장
        boolean newFamilyGroupLeaderExists = newFamilyGroupDownstream.existsByLeaderUserIdInAndTermId(userIds, term.getId());

        if (newFamilyGroupLeaderExists) {
            throw new IllegalStateException("해당 텀에 이미 새가족 순장으로 배정된 유저가 포함되어 있습니다.");
        }

        // 새가족 순원
        boolean newFamilyGroupMemberExists = newFamilyGroupMemberDownstream.existsByUserIdInAndTermId(userIds, term.getId());

        if (newFamilyGroupMemberExists) {
            throw new IllegalStateException("해당 텀에 이미 새가족 순원으로 배정된 유저가 포함되어 있습니다.");
        }

        // 새가족
        boolean newFamilyExists = newFamilyDownstream.containsNewFamilyByUserIds(userIds);

        if (newFamilyExists) {
            throw new IllegalStateException("새가족을 내부 인원으로 배정할 수 없습니다.");
        }
    }
}
