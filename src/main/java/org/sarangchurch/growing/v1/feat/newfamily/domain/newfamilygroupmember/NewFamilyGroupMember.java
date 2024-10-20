package org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroupmember;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "new_family_group_member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NewFamilyGroupMember extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "term_id", nullable = false)
    private Long termId;

    @Column(name = "new_family_group_id", nullable = false)
    private Long newFamilyGroupId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Builder
    public NewFamilyGroupMember(Long termId, Long newFamilyGroupId, Long userId) {
        this.termId = termId;
        this.newFamilyGroupId = newFamilyGroupId;
        this.userId = userId;
    }
}
