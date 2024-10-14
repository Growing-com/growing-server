package org.sarangchurch.growing.v1.feat.term.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroup.SmallGroup;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroup.SmallGroupRepository;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroupleader.SmallGroupLeader;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroupleader.SmallGroupLeaderRepository;
import org.sarangchurch.growing.v1.feat.term.infra.stream.user.UserDownstream;
import org.sarangchurch.growing.v1.feat.user.domain.User;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SmallGroupAppender {
    private final UserDownstream userDownstream;
    private final SmallGroupLeaderRepository smallGroupLeaderRepository;
    private final SmallGroupRepository smallGroupRepository;

    public void append(Long termId, Long codyId, Long userId) {
        User user = userDownstream.findById(userId);

//         boolean isActiveUser = user.isActive()
//        if (!isActiveUser) {
//            throw
//        }

        boolean existsSmallGroupLeader = smallGroupLeaderRepository.existsByTermIdAndUserId(termId, userId);

        if (existsSmallGroupLeader) {
            throw new IllegalStateException("이미 존재하는 순장입니다.");
        }

        SmallGroupLeader smallGroupLeader = SmallGroupLeader.builder()
                .termId(termId)
                .userId(userId)
                .build();

        SmallGroupLeader savedSmallGroupLeader = smallGroupLeaderRepository.save(smallGroupLeader);

        boolean existsSmallGroup = smallGroupRepository.existsByCodyIdAndSmallGroupLeaderId(codyId, smallGroupLeader.getId());

        if (existsSmallGroup) {
            throw new IllegalStateException("이미 존재하는 순모임입니다.");
        }

        SmallGroup smallGroup = SmallGroup.builder()
                .termId(termId)
                .codyId(codyId)
                .smallGroupLeaderId(savedSmallGroupLeader.getId())
                .build();

        smallGroupRepository.save(smallGroup);
    }
}
