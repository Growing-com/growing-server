package org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroup;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "new_family_group")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NewFamilyGroup extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "term_id", nullable = false)
    private Long termId;

    @Column(name = "cody_id", nullable = false)
    private Long codyId;

    @Column(name = "leader_user_id", nullable = false)
    private Long leaderUserId;

    @Builder
    public NewFamilyGroup(Long termId, Long codyId, Long leaderUserId) {
        this.termId = termId;
        this.codyId = codyId;
        this.leaderUserId = leaderUserId;
    }
}
