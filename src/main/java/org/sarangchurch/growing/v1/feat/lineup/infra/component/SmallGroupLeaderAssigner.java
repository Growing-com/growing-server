package org.sarangchurch.growing.v1.feat.lineup.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.lineup.domain.smallgroupleaderlineup.SmallGroupLeaderLineUp;
import org.sarangchurch.growing.v1.feat.lineup.domain.stumplineup.StumpLineUp;
import org.sarangchurch.growing.v1.feat.lineup.infra.data.smallgroupleaderlineup.SmallGroupLeaderLineUpWriter;
import org.sarangchurch.growing.v1.feat.lineup.infra.data.stumplineup.StumpLineUpFinder;
import org.sarangchurch.growing.v1.feat.lineup.infra.data.stumplineup.StumpLineUpWriter;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.sarangchurch.growing.v1.feat.user.domain.user.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SmallGroupLeaderAssigner {
    private final StumpLineUpFinder stumpLineUpFinder;
    private final StumpLineUpWriter stumpLineUpWriter;
    private final NormalLineUpAvailableValidator normalLineUpAvailableValidator;
    private final SmallGroupLeaderLineUpWriter smallGroupLeaderLineUpWriter;

    @Transactional
    public void assign(Term term, User codyUser, List<User> smallGroupLeaderUsers) {
        StumpLineUp stumpLineUp = stumpLineUpFinder.findByTermId(term.getId())
                .orElseGet(() ->
                        stumpLineUpWriter.save(
                                StumpLineUp.builder()
                                        .termId(term.getId())
                                        .build()
                        )
                );

        if (!stumpLineUp.containsCody(codyUser)) {
            throw new IllegalStateException("코디가 아닌 지체에게 일반 순장을 배정할 수 없습니다.");
        }

        smallGroupLeaderLineUpWriter.deleteByCodyUserIdAndTermId(codyUser.getId(), term.getId());

        smallGroupLeaderUsers.forEach(user -> normalLineUpAvailableValidator.validate(term, user));

        List<SmallGroupLeaderLineUp> smallGroupLeaderLineUps = smallGroupLeaderUsers.stream()
                .map(it ->
                        SmallGroupLeaderLineUp.builder()
                                .termId(term.getId())
                                .codyUserId(codyUser.getId())
                                .leaderUserId(it.getId())
                                .build()
                )
                .collect(Collectors.toList());

        smallGroupLeaderLineUpWriter.saveAll(smallGroupLeaderLineUps);
    }
}
