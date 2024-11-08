package org.sarangchurch.growing.v1.feat.lineup.domain.newfamilygroupleaderlineup;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "new_family_group_leader_line_up")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NewFamilyGroupLeaderLineUp extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "term_id", nullable = false)
    private Long termId;

    @Column(name = "cody_user_id", nullable = false)
    private Long codyUserId;

    @Column(name = "leader_user_id", nullable = false)
    private Long leaderUserId;

    @Builder
    public NewFamilyGroupLeaderLineUp(Long termId, Long codyUserId, Long leaderUserId) {
        this.termId = termId;
        this.codyUserId = codyUserId;
        this.leaderUserId = leaderUserId;
    }
}
