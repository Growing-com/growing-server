package org.sarangchurch.growing.v1.feat.term.domain.smallgroupmember;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.BaseEntity;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroup.SmallGroup;

import javax.persistence.*;

@Entity
@Table(name = "small_group_member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SmallGroupMember extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "term_id", nullable = false)
    private Long termId;

    @Column(name = "small_group_id", nullable = false)
    private Long smallGroupId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    // TODO: ADD isTransferred??

    @Builder
    public SmallGroupMember(Long termId, Long smallGroupId, Long userId) {
        this.termId = termId;
        this.smallGroupId = smallGroupId;
        this.userId = userId;
    }

    public void updateSmallGroup(SmallGroup targetSmallGroup) {
        this.smallGroupId = targetSmallGroup.getId();
    }
}
