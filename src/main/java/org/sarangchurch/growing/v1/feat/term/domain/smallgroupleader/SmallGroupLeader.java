package org.sarangchurch.growing.v1.feat.term.domain.smallgroupleader;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "small_group_leader")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// TODO: UNIQUE(termId, userId)
public class SmallGroupLeader extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "term_id", nullable = false)
    private Long termId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Builder
    public SmallGroupLeader(Long termId, Long userId) {
        this.termId = termId;
        this.userId = userId;
    }
}
