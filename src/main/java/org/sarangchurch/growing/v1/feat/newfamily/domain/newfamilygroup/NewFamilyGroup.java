package org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroup;

import lombok.AccessLevel;
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

    @Column(name = "new_family_group_leader_id", nullable = false)
    private Long newFamilyGroupLeaderId;
}
