package org.sarangchurch.growing.v1.feat.lineup.domain.smallgroupmemberlineup;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "small_group_member_line_up")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SmallGroupMemberLineUp extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "term_id", nullable = false)
    private Long termId;

    @Column(name = "small_group_leader_line_up_id", nullable = false)
    private Long smallGroupLeaderLineUpId;

    @Column(name = "member_user_id", nullable = false)
    private Long memberUserId;

    @Builder
    public SmallGroupMemberLineUp(Long termId, Long smallGroupLeaderLineUpId, Long memberUserId) {
        this.termId = termId;
        this.smallGroupLeaderLineUpId = smallGroupLeaderLineUpId;
        this.memberUserId = memberUserId;
    }
}
