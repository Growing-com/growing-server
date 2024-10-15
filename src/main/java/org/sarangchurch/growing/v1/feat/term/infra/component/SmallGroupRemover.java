package org.sarangchurch.growing.v1.feat.term.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroup.SmallGroup;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroup.SmallGroupRepository;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroupleader.SmallGroupLeaderRepository;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroupmember.SmallGroupMemberRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class SmallGroupRemover {
    private final SmallGroupRepository smallGroupRepository;
    private final SmallGroupMemberRepository smallGroupMemberRepository;
    private final SmallGroupLeaderRepository smallGroupLeaderRepository;

    @Transactional
    public void remove(Long id) {
        long smallGroupMemberCount = smallGroupMemberRepository.countBySmallGroupId(id);

        if (smallGroupMemberCount > 0) {
            throw new IllegalStateException("현재 순모임에 존재하는 순원이 있어서 삭제할 수 없습니다.");
        }

        SmallGroup smallGroup = smallGroupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 순모임입니다."));

        smallGroupRepository.deleteById(smallGroup.getId());
        smallGroupLeaderRepository.deleteById(smallGroup.getSmallGroupLeaderId());
    }
}
