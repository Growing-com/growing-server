package org.sarangchurch.growing.v2.term.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sarangchurch.growing.core.types.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "small_group")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SmallGroup extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "term_id", nullable = false)
    private Long termId;

    // 간사도 나중에 포함해야하나?

    @Column(name = "small_group_leader_id", nullable = false)
    private Long smallGroupLeaderId;
}
