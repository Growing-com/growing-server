package org.sarangchurch.growing.v1.feat.lineup.domain.newfamilygroupmemberlineup;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "new_family_group_member_line_up")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NewFamilyGroupMemberLineUp extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "term_id", nullable = false)
    private Long termId;

    @Column(name = "new_family_group_leader_line_up_id", nullable = false)
    private Long newFamilyGroupLeaderLineUpId;

    @Column(name = "member_user_id", nullable = false)
    private Long memberUserId;

    @Builder
    public NewFamilyGroupMemberLineUp(Long termId, Long newFamilyGroupLeaderLineUpId, Long memberUserId) {
        this.termId = termId;
        this.newFamilyGroupLeaderLineUpId = newFamilyGroupLeaderLineUpId;
        this.memberUserId = memberUserId;
    }
}
