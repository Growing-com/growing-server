package org.sarangchurch.growing.v1.feat.term.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroup.SmallGroup;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroupmember.SmallGroupMember;
import org.sarangchurch.growing.v1.feat.term.infra.data.SmallGroupFinder;
import org.sarangchurch.growing.v1.feat.term.infra.data.SmallGroupMemberFinder;
import org.sarangchurch.growing.v1.feat.term.infra.data.SmallGroupMemberWriter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SmallGroupMemberAppender {
    private final SmallGroupMemberFinder smallGroupMemberFinder;
    private final SmallGroupFinder smallGroupFinder;
    private final SmallGroupMemberWriter smallGroupMemberWriter;

    public void append(Long userId, Long smallGroupId) {
        boolean alreadyExists = smallGroupMemberFinder.existsByUserIdAndSmallGroupId(userId, smallGroupId);

        if (alreadyExists) {
            throw new IllegalArgumentException("이미 순원으로 등록되어있습니다.");
        }

        SmallGroup smallGroup = smallGroupFinder.findByIdOrThrow(smallGroupId);

        smallGroupMemberWriter.save(
                SmallGroupMember.builder()
                        .termId(smallGroup.getTermId())
                        .userId(userId)
                        .smallGroupId(smallGroupId)
                        .build()
        );
    }
}
