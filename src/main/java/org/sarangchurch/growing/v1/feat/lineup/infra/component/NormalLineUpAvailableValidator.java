package org.sarangchurch.growing.v1.feat.lineup.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.Duty;
import org.sarangchurch.growing.v1.feat.lineup.domain.stumplineup.StumpLineUp;
import org.sarangchurch.growing.v1.feat.lineup.infra.data.newfamilygroupleaderlineup.NewFamilyGroupLeaderLineUpReader;
import org.sarangchurch.growing.v1.feat.lineup.infra.data.newfamilygroupmemberlineup.NewFamilyGroupMemberLineUpReader;
import org.sarangchurch.growing.v1.feat.lineup.infra.data.smallgroupleaderlineup.SmallGroupLeaderLineUpReader;
import org.sarangchurch.growing.v1.feat.lineup.infra.data.smallgroupmemberlineup.SmallGroupMemberLineUpReader;
import org.sarangchurch.growing.v1.feat.lineup.infra.data.stumplineup.StumpLineUpFinder;
import org.sarangchurch.growing.v1.feat.lineup.infra.stream.newfamily.NewFamilyDownstream;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.sarangchurch.growing.v1.feat.user.domain.user.User;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NormalLineUpAvailableValidator {
    private final StumpLineUpFinder stumpLineUpFinder;
    private final NewFamilyDownstream newFamilyDownstream;
    private final SmallGroupLeaderLineUpReader smallGroupLeaderLineUpReader;
    private final NewFamilyGroupLeaderLineUpReader newFamilyGroupLeaderLineUpReader;
    private final SmallGroupMemberLineUpReader smallGroupMemberLineUpReader;
    private final NewFamilyGroupMemberLineUpReader newFamilyGroupMemberLineUpReader;

    public void validateDutyAssignable(Term term, User user, Duty duty) {
        if (!user.isActive()) {
            throw new IllegalStateException("비활성 유저를 라인업할 수 없습니다.");
        }

        boolean isNewFamily = newFamilyDownstream.isNewFamilyByUserId(user.getId());

        if (isNewFamily) {
            throw new IllegalStateException("새가족은 새가족 라인업을 진행해야합니다.");
        }

        StumpLineUp stumpLineUp = stumpLineUpFinder.findByTermId(term.getId())
                .orElseThrow(() -> new IllegalArgumentException("그루터기 라인업이 존재하지 않습니다."));

        switch (duty) {
            case PASTOR: {
                validateCody(user, stumpLineUp);
                validateSmallGroupLeader(term, user);
                validateNewFamilyGroupLeader(term, user);
                validateSmallGroupMember(term, user);
                validateNewFamilyGroupMember(term, user);
                break;
            }
            case CODY: {
                validatePastor(user, stumpLineUp);
                validateSmallGroupLeader(term, user);
                validateNewFamilyGroupLeader(term, user);
                validateSmallGroupMember(term, user);
                validateNewFamilyGroupMember(term, user);
                break;
            }
            case SMALL_GROUP_LEADER: {
                validatePastor(user, stumpLineUp);
                validateCody(user, stumpLineUp);
                validateNewFamilyGroupLeader(term, user);
                validateSmallGroupMember(term, user);
                validateNewFamilyGroupMember(term, user);
                break;
            }
            case NEW_FAMILY_GROUP_LEADER: {
                validatePastor(user, stumpLineUp);
                validateCody(user, stumpLineUp);
                validateSmallGroupLeader(term, user);
                validateSmallGroupMember(term, user);
                validateNewFamilyGroupMember(term, user);
                break;
            }
            case SMALL_GROUP_MEMBER: {
                validatePastor(user, stumpLineUp);
                validateCody(user, stumpLineUp);
                validateSmallGroupLeader(term, user);
                validateNewFamilyGroupLeader(term, user);
                validateNewFamilyGroupMember(term, user);
                break;
            }
            case NEW_FAMILY_GROUP_MEMBER: {
                validatePastor(user, stumpLineUp);
                validateCody(user, stumpLineUp);
                validateSmallGroupLeader(term, user);
                validateNewFamilyGroupLeader(term, user);
                validateSmallGroupMember(term, user);
                break;
            }
            default:
                break;
        }
    }

    private void validateNewFamilyGroupMember(Term term, User user) {
        boolean isAlreadyNewFamilyGroupMember = newFamilyGroupMemberLineUpReader.existsByMemberUserIdAndTermId(
                user.getId(), term.getId()
        );

        if (isAlreadyNewFamilyGroupMember) {
            throw new IllegalStateException("이미 해당텀에 새가족 순원으로 라인업된 유저입니다.");
        }
    }

    private void validateSmallGroupMember(Term term, User user) {
        boolean isAlreadySmallGroupMember = smallGroupMemberLineUpReader.existsByMemberUserIdAndTermId(
                user.getId(), term.getId()
        );

        if (isAlreadySmallGroupMember) {
            throw new IllegalStateException("이미 해당텀에 일반 순원으로 라인업된 유저입니다.");
        }
    }

    private void validateNewFamilyGroupLeader(Term term, User user) {
        boolean isAlreadyNewFamilyGroupLeader = newFamilyGroupLeaderLineUpReader.existsByLeaderUserIdAndTermId(
                user.getId(), term.getId()
        );

        if (isAlreadyNewFamilyGroupLeader) {
            throw new IllegalStateException("이미 해당텀에 새가족 순장으로 라인업된 유저입니다.");
        }
    }

    private void validateSmallGroupLeader(Term term, User user) {
        boolean isAlreadySmallGroupLeader = smallGroupLeaderLineUpReader.existsByLeaderUserIdAndTermId(
                user.getId(), term.getId()
        );

        if (isAlreadySmallGroupLeader) {
            throw new IllegalStateException("이미 해당텀에 일반 순장으로 라인업된 유저입니다.");
        }
    }

    private void validatePastor(User user, StumpLineUp stumpLineUp) {
        boolean isAlreadySeniorPastor = user.getId().equals(stumpLineUp.getSeniorPastorUserId());

        if (isAlreadySeniorPastor) {
            throw new IllegalStateException("이미 해당텀에 담당 교역자로 라인업된 유저입니다.");
        }

        boolean isAlreadyJuniorPastor = stumpLineUp.getJuniorPastorUserIds().contains(user.getId());

        if (isAlreadyJuniorPastor) {
            throw new IllegalStateException("이미 해당텀에 부교역자로 라인업된 유저입니다.");
        }
    }
    
    private void validateCody(User user, StumpLineUp stumpLineUp) {
        boolean isAlreadyCody = stumpLineUp.getCodyUserIds().contains(user.getId());

        if (isAlreadyCody) {
            throw new IllegalStateException("이미 해당텀에 코디로 라인업된 유저입니다.");
        }
    }
}
